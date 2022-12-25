from django.shortcuts import render
from django.http import HttpResponse, JsonResponse
from django.apps import apps
from django.contrib.postgres.search import SearchQuery, SearchVector, SearchRank
##for some reason django crashes with multiple relative imports... Below might be the reason
# http://python-notes.curiousefficiency.org/en/latest/python_concepts/import_traps.html
from .routing.strategy import *
from .routing.context import *


# Create your views here.
def index(request, lang):
    test()
    return HttpResponse("Hello, world. You're at the Word index.")

def home(request, lang):
    home = True
    if lang.lower() == 'bhutia':
        return render(request, 'languages/bhutia/home_bhutia.html', {'home': home, 'bhutia': True})
    if lang.lower() == 'english':
        return render(request, 'languages/english/home_english.html', {'home': home, 'english': True})

def master_list(request, lang):
    #get dictionary pack
    target = apps.get_model('dictionaries', lang) 
    master = target.objects.all()
    print(master)
    if lang == 'bhutia':
        return render(request,'languages/bhutia/master_list.html', {'master':master, 'bhutia': True})
    if lang == 'english':
        return render(request,'languages/english/master_list.html', {'master':master, 'english': True})
 
def exact(request, lang, translation, entry_id):
    if lang.lower() == 'bhutia':
            context = SearchContext(BhutiaStrategy())
    if lang.lower() == "english":
        context = SearchContext(EnglishStrategy())
    return context.execute_strategy(request, lang, translation, entry_id)

def search(request, lang, translation):
    error = False  
    #FIXME 
    if 'query' in request.GET:
        context = None
        if lang.lower() == 'bhutia':
            context = SearchContext(BhutiaStrategy())
        if lang.lower() == "english":
            context = SearchContext(EnglishStrategy())
        return context.execute_strategy(request, lang, translation, None)
        
    return HttpResponse("An unknown error occured.")

