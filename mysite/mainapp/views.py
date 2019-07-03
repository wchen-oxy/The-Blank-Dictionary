from django.shortcuts import render
from django.http import HttpResponse
from .models import Word
from django.contrib.postgres.search import SearchQuery, SearchVector, SearchRank



# Create your views here.
def index(request):
    return HttpResponse("Hello, world. You're at the Word index.")

def entry(request):
    if 'query' in request.GET:
        query = request.GET['query']
        print(query)
        return render(request, 'entry.html')
    error = True
    return render(request, 'entry.html', {'error': error})

def search(request):
    error = False
    if 'query' in request.GET:
        query = request.GET['query']

        if not query:
            error = True
        else:
            if request.path == "/entry/bhutia_english/":
                exact_entry = Word.objects.filter(bhut_trans__iexact=query)
                entries = Word.objects.filter(bhut_trans__icontains=query)
                return render(request, 'entry.html', {'possible': entries, 'bhu_eng_exact': exact_entry})

            if request.path == "/entry/english_bhutia/":
                exact_entry = Word.objects.filter(eng_trans__iexact=query)
                entries = Word.objects.filter(eng_trans__icontains=query)
                return render(request, 'entry.html', {'possible': entries, 'eng_bhut_exact': exact_entry})

            if request.path == "/entry/tibetan_bhutia/":
                exact_entry = Word.objects.filter(tib_trans__iexact=query)
                entries = Word.objects.filter(tib_trans__icontains=query)
                return render(request, 'entry/entry_list.html', {'possible': entries, 'tib_bhut_exact': exact_entry})
        return render(request, 'entry.html', {'error': error})
    # return render(request, 'entry.html', {'error': error})
