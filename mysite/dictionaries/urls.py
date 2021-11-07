from django.urls import path
from django.conf.urls import url
from . import views
# from .views import AllBhutia


urlpatterns = [
    path('<slug:lang>/', views.home, name="home"),
    path('<slug:lang>/about/', views.lang_about, name="lang_about"),
    path('<slug:lang>/master/', views.master_list, name="master_list"),
    path('<slug:lang>/suggest', views.suggest, name="suggest-word"),
    path('<slug:lang>/email', views.email, name="email"),
    path('<slug:lang>/<slug:translation>/exact/<slug:entry_id>/',
         views.exact, name="exact"),
    path('<slug:lang>/<slug:translation>/', views.search, name="search")

]
