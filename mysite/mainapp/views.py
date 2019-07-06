from django.shortcuts import render
from django.http import HttpResponse
from .models import Bhutia
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


def search(request, translation):
    error = False
    if 'query' in request.GET:
        query = request.GET['query']

        if not query:
            error = True
            return render(request, 'entry.html', {'error': error})

        elif translation == "bhutia_english":
            exact_entry = Bhutia.objects.filter(romanization__iexact=query)
            entries = Bhutia.objects.filter(romanization__icontains=query)
            if not exact_entry and not entries:
                error = True
                return render(request, 'entry.html', {'error': error})
            return render(request, 'entry.html', {'be_possible': entries, 'bhut_eng_exact': exact_entry})

        elif translation == "english_bhutia":
            exact_entry = Bhutia.objects.filter(eng_trans__iexact=query)
            entries = Bhutia.objects.filter(eng_trans__icontains=query)
            # print(exact_entry, entries)
            if not exact_entry and not entries:
                error = True
                return render(request, 'entry.html', {'error': error})
            return render(request, 'entry.html', {'eb_possible': entries, 'eng_bhut_exact': exact_entry})

        elif translation == "tibetan_bhutia":
            exact_entry = Bhutia.objects.filter(tib_script__iexact=query)
            entries = Bhutia.objects.filter(tib_script__icontains=query)
            if not exact_entry and not entries:
                error = True
                return render(request, 'entry.html', {'error': error})
            return render(request, 'entry.html', {'tb_possible': entries, 'tib_bhut_exact': exact_entry})

    return render(request, 'entry.html', {'error': error})

