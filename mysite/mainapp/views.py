from django.shortcuts import render
from django.http import HttpResponse

# Create your views here.
def index(request):
    return HttpResponse("Hello, world. You're at the mainapp index.")

def entry(request):
    if 'query' in request.GET:
        query = request.GET['query']
        print(query)
        if not query:
            error = True
        else:
            return render(request, 'entry.html')
        return render(request, 'entry.html', {'error': error})
