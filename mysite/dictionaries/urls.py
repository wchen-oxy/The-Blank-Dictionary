from django.urls import path
from django.conf.urls import url
from . import views
from .views import AllBhutia



urlpatterns = [
    # path('', views.index, name="index"),
    # path('', views.entry, name="entry"),
    # path('bhutia_english/', views.search, name="bhutia to english"),
    # path('english_bhutia/', views.search, name="english to bhutia"),
    # path('tibetan_bhutia/', views.search, name="tibetan to bhutia"),
    path('<slug:lang>/<slug:translation>/', views.search, name="search"),
    path('<slug:lang>/', AllBhutia.as_view(), name="all-words")

]