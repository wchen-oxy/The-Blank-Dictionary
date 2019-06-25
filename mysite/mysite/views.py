from django.shortcuts import render
from django.http import HttpResponse

def home(request):
    # return HttpResponse("Hello, world. You're at the home index.")
    home = True
    return render(request, 'home.html', {'home': home})

def search(request):
    return HttpResponse("Hello, world. You're at the home index.")