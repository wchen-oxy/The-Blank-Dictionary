from django.shortcuts import render
from django.http import HttpResponse, JsonResponse
from django.core import serializers
from django.apps import apps
from django.urls import reverse

def home(request):
    # return HttpResponse("Hello, world. You're at the home index.")
    home = True
    return render(request, 'home.html', {'home': home})

def test(request):
    return HttpResponse("Site works!")

def download(request, pack):
    data = serializers.serialize("json", apps.get_model("mainapp", pack).objects.all())
    return JsonResponse(data, safe=False)