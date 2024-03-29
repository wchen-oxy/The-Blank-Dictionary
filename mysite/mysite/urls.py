"""mysite URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/2.2/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import include, path, re_path
from .views import AllBhutia


from . import views

urlpatterns = [
    path('admin/', admin.site.urls),
    path('', views.home),
    path('about/', views.about, name="about"),
    path('involvement/', views.involve, name="involve"),
    path('d/', include('dictionaries.urls')),
    path('download/<slug:pack>', views.download),
    path('apitest/<slug:lang>/', AllBhutia.as_view(), name="all-words"),
    path('api/<slug:lang>/', views.test, name="test-words"),
    path('api/<slug:lang>/<slug:word', views.test, name="test-words"),
    path('all/', views.returnAll, name="return-all"),
    path('status/', views.status, name="status"),
    path('updates/', views.updates, name="updates"),
    path('accounts/', include('django.contrib.auth.urls')),
]
