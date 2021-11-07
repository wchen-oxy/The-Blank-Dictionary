from django import forms

# Create your forms here.

class ContactForm(forms.Form):
	name = forms.CharField(max_length = 100)
	contact = forms.CharField(max_length = 200)
	message = forms.CharField(widget=forms.Textarea)
