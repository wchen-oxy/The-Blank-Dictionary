from django.shortcuts import render
from django.http import HttpResponse, JsonResponse
from django.core import serializers
from django.apps import apps
from django.urls import reverse
#API STUFF
#API Specific imports
from rest_framework.parsers import JSONParser
from .serializers import BhutiaSerializer
from rest_framework import generics


def home(request):
    # return HttpResponse("Hello, world. You're at the home index.")
    home = True
    return render(request, 'home.html', {'home': home})

def test(request):
    return HttpResponse("Site works!")

def download(request, pack):
    data = serializers.serialize("json", apps.get_model("mainapp", pack).objects.all())
    return JsonResponse(data, safe=False)

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
