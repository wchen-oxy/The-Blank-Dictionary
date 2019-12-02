from django.shortcuts import render
from django.http import HttpResponse, JsonResponse
from django.apps import apps
# import .models
from django.contrib.postgres.search import SearchQuery, SearchVector, SearchRank
#API Specific imports
# from rest_framework.parsers import JSONParser
# from .serializers import BhutiaSerializer
# from rest_framework import generics

##for some reason django crashes with multiple relative imports... Below might be the reason
# http://python-notes.curiousefficiency.org/en/latest/python_concepts/import_traps.html
from .routing.strategy import *
from .routing.context import *


# Create your views here.
def index(request, lang):
    test()
    return HttpResponse("Hello, world. You're at the Word index.")

# def entry(request, lang):
#     if 'query' in request.GET:
#         query = request.GET['query']
#         print(query)
#         return render(request, 'entry.html')
#     error = True
#     return render(request, 'entry.html', {'error': error})

# def search(request):
#     error = False
#     if 'query' in request.GET:
#         query = request.GET['query']
#
#         if not query:
#             error = True
#         else:
#             if request.path == "/entry/bhutia_english/":
#                 exact_entry = Word.objects.filter(bhut_trans__iexact=query)
#                 entries = Word.objects.filter(bhut_trans__icontains=query)
#                 return render(request, 'entry.html', {'possible': entries, 'bhu_eng_exact': exact_entry})
#
#             if request.path == "/entry/english_bhutia/":
#                 exact_entry = Word.objects.filter(eng_trans__iexact=query)
#                 entries = Word.objects.filter(eng_trans__icontains=query)
#                 return render(request, 'entry.html', {'possible': entries, 'eng_bhut_exact': exact_entry})
#
#             if request.path == "/entry/tibetan_bhutia/":
#                 exact_entry = Word.objects.filter(tib_trans__iexact=query)
#                 entries = Word.objects.filter(tib_trans__icontains=query)
#                 return render(request, 'entry/entry_list.html', {'possible': entries, 'tib_bhut_exact': exact_entry})
#         return render(request, 'entry.html', {'error': error})
#     # return render(request, 'entry.html', {'error': error})

def home(request, lang):
        # return HttpResponse("Hello, world. You're at the home index.")
    home = True
    if lang.lower() == 'bhutia':
        return render(request, 'bhutia/home_bhutia.html', {'home': home})
    if lang.lower() == 'english':
        return HttpResponse("Hello, world. You're at the english index.")


def search(request, lang, translation):
    print(translation)
    print(lang)
    error = False
    
    # if 'query' in request.GET:
    #     query = request.GET['query']
    #     #get dictionary pack
    #     target = apps.get_model('dictionaries', lang) 
    #     all_tran = {
    #     "bhutia_english": ["be_possible", "bhut_eng_exact"],
    #     "english_bhutia": ["eb_possible", "eng_bhut_exact"],
    #     "tibetan_bhutia": ["tb_possible", "tib_bhut_exact"]
    #     }    

    #     params = {
    #         "bhutia_english": [{"romanization__iexact": query}, {"romanization__icontains": query}],
    #         "english_bhutia": [{"eng_trans__iexact": query}, {"eng_trans__icontains":query}],
    #         "tibetan_bhutia": [{"tib_script__iexact":query}, {"tib_script__icontains":query}]
    #     }
       

    #     if not query:
    #         error = True
    #         return render(request, 'entry.html', {'error': error})

    #     # for trans in ['bhutia_english', 'english_bhutia', 'tibetan_bhutia']:
    #     if translation in all_tran:
            
    #         exact_entry = target.objects.filter(**params.get(translation)[0])
    #         entries = target.objects.filter(**params.get(translation)[1])
    #         if not exact_entry and not entries:
    #             error = True
    #             return render(request, 'entry.html', {'error': error})
    #         return render(request, 'entry.html', {all_tran.get(translation)[0]: entries, all_tran.get(translation)[1]: exact_entry})


        # elif translation == "bhutia_english":
        #     exact_entry = target.objects.filter(romanization__iexact=query)
        #     entries = target.objects.filter(romanization__icontains=query)
        #     if not exact_entry and not entries:
        #         error = True
        #         return render(request, 'entry.html', {'error': error})
        #     return render(request, 'entry.html', {'be_possible': entries, 'bhut_eng_exact': exact_entry})

        # elif translation == "english_bhutia":
        #     exact_entry = target.objects.filter(eng_trans__iexact=query)
        #     entries = target.objects.filter(eng_trans__icontains=query)
        #     # print(exact_entry, entries)
        #     if not exact_entry and not entries:
        #         error = True
        #         return render(request, 'entry.html', {'error': error})
        #     return render(request, 'entry.html', {'eb_possible': entries, 'eng_bhut_exact': exact_entry})

        # elif translation == "tibetan_bhutia":
        #     exact_entry = target.objects.filter(tib_script__iexact=query)
        #     entries = target.objects.filter(tib_script__icontains=query)
        #     if not exact_entry and not entries:
        #         error = True
        #         return render(request, 'entry.html', {'error': error})
        #     return render(request, 'entry.html', {'tb_possible': entries, 'tib_bhut_exact': exact_entry})
    
    #FIXME 
    if 'query' in request.GET:
        context = None
        if lang.lower() == 'bhutia':
            context = SearchContext(BhutiaStrategy())
        if lang.lower() == "english":
            context = SearchContext(EnglishStrategy())
        return context.execute_strategy(request, lang, translation)
        
    return render(request, 'entry.html', {'error': error})

