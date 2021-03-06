# Generated by Django 2.2.2 on 2019-11-21 00:00

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('dictionaries', '0004_auto_20191022_0035'),
    ]

    operations = [
        migrations.CreateModel(
            name='English',
            fields=[
                ('word', models.CharField(max_length=50, primary_key=True, serialize=False)),
                ('ipa', models.CharField(blank=True, max_length=100, null=True)),
                ('definition', models.CharField(max_length=200)),
                ('example', models.CharField(blank=True, max_length=400, null=True)),
            ],
            options={
                'verbose_name': 'English Entry',
                'verbose_name_plural': 'English',
                'db_table': 'English',
                'managed': False,
            },
        ),
    ]
