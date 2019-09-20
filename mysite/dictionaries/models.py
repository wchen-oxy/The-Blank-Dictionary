# This is an auto-generated Django model module.
# You'll have to do the following manually to clean this up:
#   * Rearrange models' order
#   * Make sure each model has one field with primary_key=True
#   * Make sure each ForeignKey has `on_delete` set to the desired behavior.
#   * Remove `managed = False` lines if you wish to allow Django to create, modify, and delete the table
# Feel free to rename the models, but don't rename db_table values or field names.
from django.db import models


class Bhutia(models.Model):
    romanization = models.CharField(primary_key=True, max_length=100)
    ipa = models.CharField(max_length=100, blank=True, null=True)
    category = models.CharField(max_length=100, blank=True, null=True)
    eng_trans = models.CharField(max_length=100, blank=True, null=True)
    tib_script = models.CharField(max_length=100, blank=True, null=True)
    example = models.CharField(max_length=100, blank=True, null=True)

    #use this only for testing the Python Shell!! Django recommends against overriding __init__
    # def __init__(self, romanization, ipa=None, category=None, eng_trans=None, tib_script=None, example=None):
        
    #     self.romanization = romanization
    #     self.ipa = ipa 
    #     self.category = category 
    #     self.eng_trans = eng_trans 
    #     self.tib_script = tib_script 
    #     self.example = example 

    class Meta:
        managed = False
        db_table = 'Bhutia'
        verbose_name = 'Bhutia Entry'
        verbose_name_plural = 'Bhutia'

    def __str__(self):
        template = '{0.romanization}'
        return template.format(self)
