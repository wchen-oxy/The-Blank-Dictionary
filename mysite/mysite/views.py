from django.shortcuts import render, redirect
from .forms import ContactForm
from django.core.mail import send_mail, BadHeaderError
from django.http import HttpResponse, JsonResponse, HttpResponseBadRequest
from django.core import serializers
from django.apps import apps
from django.urls import reverse
# API STUFF
# API Specific imports
from rest_framework.parsers import JSONParser
from .serializers import BhutiaSerializer, EnglishSerializer
from rest_framework import generics
import dictionaries
from django.views.decorators.csrf import csrf_exempt
import hashlib
import json

# API TEST STUFF
# def home(request):
#     # return HttpResponse("Hello, world. You're at the home index.")
#     home = True
#     return render(request, 'home.html', {'home': home})


def home(request):
    return render(request, "main_home.html")


def about(request):
    return render(request, 'main_about.html')


def involve(request):
    return render(request, 'main_involve.html')


def contact(request):
    return render(request, "main_contact.html")


def download(request, pack):
    data = serializers.serialize(
        "json", apps.get_model("mainapp", pack).objects.all())
    return JsonResponse(data, safe=False)


class AllBhutia(generics.ListAPIView):
    """
    FIXME implement dynamically called language pack
    """
    target = apps.get_model('dictionaries', "Bhutia")
    queryset = target.objects.all()
    serializer_class = BhutiaSerializer


@csrf_exempt
def test(request, lang, word=None):
    if request.method == 'GET' and 'Authorization' in request.headers:
        if request.headers['Authorization'] != encrypt_string("Az39dB0n!23"):
            return HttpResponse("Auth Key is Wrong.")
       # FIXME NEED TO DESIGN INDIVIDUAL WORD SEARCH
        if (word == None):
            dictionary = apps.get_model("dictionaries", lang)
            dic = dictionary.objects.all()
            serializer = None
            if lang.lower() == "bhutia":
                serializer = BhutiaSerializer(dic, many=True)
            if lang.lower() == "english":
                serializer = EnglishSerializer(dic, many=True)
            return JsonResponse(serializer.data, safe=False)
        return HttpResponse("Site works, but no lang matched")
    else:
        return HttpResponse("Site works, but you have no auth key.")


@csrf_exempt
def query(request, lang, translation):
    query = request.GET['query'].strip()
    target = apps.get_model('dictionaries', lang)
    if lang.lower() == "bhutia":
        params = {
            "bhutia_english_formal": [{"bhut_rom_formal__iexact": query}, {"bhut_rom_formal__icontains": query}],
            "bhutia_english_informal": [{"bhut_rom_informal__iexact": query}, {"bhut_rom_informal__icontains": query}],
            "english_bhutia_formal": [{"eng_trans__iexact": query}, {"eng_trans__icontains": query}],
            "english_bhutia_informal": [{"eng_trans__iexact": query}, {"eng_trans__icontains": query}],
            "bhutiascript_english_formal": [{"bhut_script_formal__iexact": query}, {"bhut_script_formal__icontains": query}],
            "bhutiascript_english_informal": [{"bhut_script_informal__iexact": query}, {"bhut_script_informal__icontains": query}]
        }
        # entries = serializers.serialize(
        #     "json", target.objects.filter(**params.get(translation)[1])[:5])
        # print(entries)
        serializer = BhutiaSerializer(target.objects.filter(
            **params.get(translation)[1])[:5], many=True)
        return JsonResponse(serializer.data, safe=False)
        # return target.objects.filter(**params.get(translation)[1])[:5]
        # return JsonResponse(entries, safe=False)


@csrf_exempt
def returnAll(request):
    models = []
    if request.method == 'GET' and 'Authorization' in request.headers:
        if request.headers['Authorization'] == encrypt_string("Az39dB0n!23"):
            for model in apps.all_models['dictionaries']:
                models.append(model.upper())
            return JsonResponse(models, safe=False)
        return HttpResponseBadRequest()
    return HttpResponseBadRequest()


@csrf_exempt
def status(request):
    if request.method == 'GET' and request.headers['Authorization'] == encrypt_string("Az39dB0n!23"):
        return HttpResponse()
    return HttpResponseBadRequest()


@csrf_exempt
def updates(request):
    if request.method == 'GET' and request.headers['Authorization'] == encrypt_string("Az39dB0n!23"):
        return JsonResponse(len(apps.all_models['dictionaries']), safe=False)
    return HttpResponseBadRequest()


def encrypt_string(hash_string):
    sha_signature = \
        hashlib.sha256(hash_string.encode()).hexdigest()
    return sha_signature
