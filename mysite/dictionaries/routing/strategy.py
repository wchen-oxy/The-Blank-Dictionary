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
        query = request.GET['query']
       
        #get dictionary pack
        target = apps.get_model('dictionaries', lang) 
       
        all_tran = {
        "bhutia_english": ["be_possible", "bhut_eng_exact"],
        "english_bhutia": ["eb_possible", "eng_bhut_exact"],
        "tibetan_bhutia": ["tb_possible", "tib_bhut_exact"]
        }    

        params = {
            "bhutia_english": [{"romanization__iexact": query}, {"romanization__icontains": query}],
            "english_bhutia": [{"eng_trans__iexact": query}, {"eng_trans__icontains":query}],
            "tibetan_bhutia": [{"tib_script__iexact":query}, {"tib_script__icontains":query}]
        }
        
        # for trans in ['bhutia_english', 'english_bhutia', 'tibetan_bhutia']:
        if translation in all_tran:
            exact_entry = target.objects.filter(**params.get(translation)[0])
            entries = target.objects.filter(**params.get(translation)[1])
            #Checker for no matching
            if not exact_entry and not entries:
                error = True
                return render(request, 'languages/bhutia/entry_bhutia.html', {'error': error})
            return render(request, 'languages/bhutia/entry_bhutia.html', {all_tran.get(translation)[0]: entries, all_tran.get(translation)[1]: exact_entry})

      
class EnglishStrategy(Strategy):
    def execute(self, query) -> HttpResponse:
        return super().execute(query)
      
