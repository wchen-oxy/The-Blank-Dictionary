from django.shortcuts import render
from django.http import HttpResponse, JsonResponse
from django.apps import apps
# import .models
from django.contrib.postgres.search import SearchQuery, SearchVector, SearchRank
#API Specific imports
from rest_framework.parsers import JSONParser
from .serializers import BhutiaSerializer
from rest_framework import generics




# Create your views here.
def index(request):
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


def search(request, lang, translation):
    error = False
    if 'query' in request.GET:
        query = request.GET['query']
       

        # from .models import lang as target
        target = apps.get_model('dictionaries', lang)

        if not query:
            error = True
            return render(request, 'entry.html', {'error': error})

        elif translation == "bhutia_english":
            exact_entry = target.objects.filter(romanization__iexact=query)
            entries = target.objects.filter(romanization__icontains=query)
            if not exact_entry and not entries:
                error = True
                return render(request, 'entry.html', {'error': error})
            return render(request, 'entry.html', {'be_possible': entries, 'bhut_eng_exact': exact_entry})

        elif translation == "english_bhutia":
            exact_entry = target.objects.filter(eng_trans__iexact=query)
            entries = target.objects.filter(eng_trans__icontains=query)
            # print(exact_entry, entries)
            if not exact_entry and not entries:
                error = True
                return render(request, 'entry.html', {'error': error})
            return render(request, 'entry.html', {'eb_possible': entries, 'eng_bhut_exact': exact_entry})

        elif translation == "tibetan_bhutia":
            exact_entry = target.objects.filter(tib_script__iexact=query)
            entries = target.objects.filter(tib_script__icontains=query)
            if not exact_entry and not entries:
                error = True
                return render(request, 'entry.html', {'error': error})
            return render(request, 'entry.html', {'tb_possible': entries, 'tib_bhut_exact': exact_entry})

    return render(request, 'entry.html', {'error': error})

def bhutia_download(request):
        if request.method == 'GET':
            words = Bhutia.objects.all()
            serializer = BhutiaSerializer(words, many=True)
            return JsonResponse(serializer.data, safe=False)

class AllBhutia(generics.ListAPIView):
    """
    FIXME implement dynamically called language pack
    """
    # def get(self, request, *args, **kwargs):
    # lang = self.kwargs['slug']
    # target = apps.get_model('dictionaries', lang)
    target = apps.get_model('dictionaries', "Bhutia")
    queryset = target.objects.all()
    serializer_class = BhutiaSerializer

