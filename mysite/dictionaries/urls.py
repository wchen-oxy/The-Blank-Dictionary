from django.urls import path
from . import views
# from .views import AllBhutia



urlpatterns = [
    path('<slug:lang>/', views.home, name="home"),
    path('<slug:lang>/master/', views.master_list, name="master_list"),
    path('<slug:lang>/<slug:translation>/exact/<slug:entry_id>/', views.exact, name="exact"),
    path('<slug:lang>/<slug:translation>/', views.search, name="search")

]