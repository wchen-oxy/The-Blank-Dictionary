from django.shortcuts import render
from django.http import HttpResponse, JsonResponse
from django.apps import apps
from django.core.mail import send_mail, BadHeaderError
from django.contrib.postgres.search import SearchQuery, SearchVector, SearchRank
# for some reason django crashes with multiple relative imports... Below might be the reason
# http://python-notes.curiousefficiency.org/en/latest/python_concepts/import_traps.html
from .routing.strategy import *
from .routing.context import *

from django.conf import settings
from mysite.forms import ContactForm


def index(request, lang):
    test()
    return HttpResponse("Hello, world. You're at the Word index.")


def lang_about(request, lang):
    if lang.lower() == 'bhutia':
        return render(request, 'languages/bhutia/about.html', {'bhutia': True})


def home(request, lang):
    home = True
    if lang.lower() == 'bhutia':
        return render(request, 'languages/bhutia/home_bhutia.html', {'home': home, 'bhutia': True})
    if lang.lower() == 'english':
        return render(request, 'languages/english/home_english.html', {'home': home, 'english': True})


def master_list(request, lang):
    # get dictionary pack
    target = apps.get_model('dictionaries', lang)
    master = target.objects.all()
    if lang == 'bhutia':
        return render(request, 'languages/bhutia/master_list.html', {'master': master, 'bhutia': True})
    if lang == 'english':
        return render(request, 'languages/english/master_list.html', {'master': master, 'english': True})


def exact(request, lang, translation, entry_id):
    if lang.lower() == 'bhutia':
        context = SearchContext(BhutiaStrategy())
    if lang.lower() == "english":
        context = SearchContext(EnglishStrategy())
    return context.execute_strategy(request, lang, translation, entry_id)


def search(request, lang, translation):
    error = False
    # FIXME
    print("lang", lang)
    print(request)
    if 'query' in request.GET:
        context = None
        if lang.lower() == 'bhutia':
            context = SearchContext(BhutiaStrategy())
        if lang.lower() == "english":
            context = SearchContext(EnglishStrategy())
        return context.execute_strategy(request, lang, translation, None)

    return HttpResponse("An unknown error occured.")


def email(request, lang):
    # create a variable to keep track of the form
    messageSent = False
    # check if form has been submitted
    if request.method == 'POST':
        form = ContactForm(request.POST)
        # check if data from the form is clean
        if form.is_valid():
            cd = form.cleaned_data
            subject = "Sending an email with Django"
            message = 'Contact Info: ' + \
                cd['contact'] + '\n' + 'Message: ' + cd['message']

            # send the email to the recipent
            send_mail(subject, message,
                      settings.DEFAULT_FROM_EMAIL, ['williamshengchen8@gmail.com'])

            # set the variable initially created to True
            messageSent = True

    else:
        form = ContactForm()

    return render(request, 'main_suggest.html', {
        'form': form,
        'messageSent': messageSent,

    })


def suggest(request, lang):
    # print()
    # dictionary = None
    # if lang == 'bhutia':
    #     dictionary = {lang: True}
    # return render(request, 'main_suggest.html', dictionary)
    messageSent = False
    # check if form has been submitted
    if request.method == 'POST':
        form = ContactForm(request.POST)
        # check if data from the form is clean
        if form.is_valid():
            cd = form.cleaned_data
            subject = "Sending an email with Django"
            message = 'Contact Info: ' + \
                cd['contact'] + '\n' + 'Message: ' + cd['message']

            # send the email to the recipent
            send_mail(
                subject,
                message,
                settings.DEFAULT_FROM_EMAIL,
                [settings.DEFAULT_BHUTIA_RECIPIENT],
                fail_silently=False,
            )

            # set the variable initially created to True
            messageSent = True

    else:
        form = ContactForm()

    return render(request, 'main_suggest.html', {
        'form': form,
        'messageSent': messageSent,
        'recipient': settings.DEFAULT_BHUTIA_RECIPIENT

    })
