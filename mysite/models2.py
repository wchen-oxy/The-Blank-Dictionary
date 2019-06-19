# This is an auto-generated Django model module.
# You'll have to do the following manually to clean this up:
#   * Rearrange models' order
#   * Make sure each model has one field with primary_key=True
#   * Make sure each ForeignKey has `on_delete` set to the desired behavior.
#   * Remove `managed = False` lines if you wish to allow Django to create, modify, and delete the table
# Feel free to rename the models, but don't rename db_table values or field names.
from django.db import models


class Word(models.Model):
    romanization = models.CharField(primary_key=True, max_length=100)
    ipa = models.CharField(max_length=100, blank=True, null=True)
    category = models.CharField(max_length=100, blank=True, null=True)
    eng_trans = models.CharField(max_length=100, blank=True, null=True)
    tib_trans = models.CharField(max_length=100, blank=True, null=True)
    bhut_trans = models.CharField(max_length=100, blank=True, null=True)
    example = models.CharField(max_length=100, blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'Word'
