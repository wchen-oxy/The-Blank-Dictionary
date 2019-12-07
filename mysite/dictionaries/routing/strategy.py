from abc import ABC, abstractmethod
from typing import List

from django.shortcuts import render
from django.http import HttpResponse, JsonResponse
from django.apps import apps
# import .models
from django.contrib.postgres.search import SearchQuery, SearchVector, SearchRank

def test():
    print("Test Successful")

class Strategy():
    @abstractmethod
    def execute(self, request, lang, translation):
        pass

'''BEGIN CONCRETE STRATEGIES'''

class BhutiaStrategy(Strategy):
    def execute(self, request, lang, translation) -> HttpResponse:
        if not request.GET['query']:
            error = True
            return render(request, 'languages/bhutia/entry_bhutia.html', {'error': error, 'bhutia': True})
        query = request.GET['query']
       
        #get dictionary pack
        target = apps.get_model('dictionaries', lang) 
       
        all_tran = {
        "bhutia_english_formal": ["be_f_possible", "be_f_exact"],
        "bhutia_english_informal": ["be_i_possible", "be_i_exact"],
        "english_bhutia_formal": ["eb_f_possible", "eb_f_exact"],
        "english_bhutia_informal": ["eb_i_possible", "eb_i_exact"],
        "bhutiascript_english_formal": ["bse_f_possible", "bse_f_exact"],
        "bhutiascript_english_informal": ["bse_i_possible", "bse_i_exact"]

        }    

        params = {
            "bhutia_english_formal": [{"bhut_rom_formal__iexact": query}, {"bhut_rom_formal__icontains": query}],
            "bhutia_english_informal": [{"bhut_rom_informal__iexact": query}, {"bhut_rom_informal__icontains": query}],
            "english_bhutia_formal": [{"eng_trans__iexact": query}, {"eng_trans__icontains":query}],
            "english_bhutia_informal": [{"eng_trans__iexact": query}, {"eng_trans__icontains":query}],
            "bhutiascript_english_formal": [{"bhut_script_formal__iexact":query}, {"bhut_script_formal__icontains":query}],
            "bhutiascript_english_informal": [{"bhut_script_informal__iexact":query}, {"bhut_script_informal__icontains":query}]
        }
        
        # for trans in ['bhutia_english', 'english_bhutia', 'tibetan_bhutia']:
        if translation in all_tran:
            exact_entry = target.objects.filter(**params.get(translation)[0])
            entries = target.objects.filter(**params.get(translation)[1])
            #Checker for no matching
            if len(entries) == 1:
                entries = None
            if not exact_entry and not entries:
                error = True
                return render(request, 'languages/bhutia/entry_bhutia.html', {'error': error, 'bhutia': True})
            return render(request, 'languages/bhutia/entry_bhutia.html', {all_tran.get(translation)[0]: entries, all_tran.get(translation)[1]: exact_entry, 'bhutia': True})

      
class EnglishStrategy(Strategy):
    def execute(self, request, lang, translation) -> HttpResponse:
        if not request.GET['query']:
            error = True
            return render(request, 'languages/english/entry_english.html', {'error': error})
        query = request.GET['query']

        #get dictionary pack
        target = apps.get_model('dictionaries', lang) 

        all_tran = {
        "english_english": ["ee_possible", "ee_exact"],
        }    

        params = {
            "english_english": [{"word__iexact": query}, {"word__icontains": query}],
            }
        
        # for trans in ['bhutia_english', 'english_bhutia', 'tibetan_bhutia']:
        if translation in all_tran:
            exact_entry = target.objects.filter(**params.get(translation)[0])
            entries = target.objects.filter(**params.get(translation)[1])
            if len(entries) == 1:
                entries = None
            #Checker for no matching
            if not exact_entry and not entries:
                error = True
                return render(request, 'languages/english/entry_english.html', {'error': error, 'english': True})
            return render(request, 'languages/english/entry_english.html', {all_tran.get(translation)[0]: entries, all_tran.get(translation)[1]: exact_entry, 'english': True})

      

        return super().execute(query)
      
