from django.urls import path
from django.conf.urls import url
from . import views



urlpatterns = [
    # path('', views.index, name="index"),
    # path('', views.entry, name="entry"),
    # path('bhutia_english/', views.search, name="bhutia to english"),
    # path('english_bhutia/', views.search, name="english to bhutia"),
    # path('tibetan_bhutia/', views.search, name="tibetan to bhutia"),
    path('<slug:translation>/', views.search)

]