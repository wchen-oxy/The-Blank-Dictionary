from rest_framework import serializers
from dictionaries.models import Bhutia, English


class BhutiaSerializer(serializers.Serializer):
    # id = serializers.IntegerField(read_only=True)
    # romanization = serializers.CharField(read_only=True, allow_null=True)
    # ipa = serializers.CharField(read_only=True, allow_null=True)
    # category = serializers.CharField(read_only=True, allow_null=True)
    # eng_trans = serializers.CharField(read_only=True, allow_null=True)
    # tib_script = serializers.CharField(read_only=True, allow_null=True)
    # example = serializers.CharField(read_only=True, allow_null=True)
    entry_id = serializers.IntegerField(read_only=True)
    # ipa = serializers.CharField(read_only=True, allow_null=False)
    # category = serializers.CharField(read_only=True, allow_null=True)
    eng_trans = serializers.CharField(read_only=True, allow_null=True)
    bhut_rom_formal = serializers.CharField(read_only=True, allow_null=True)
    bhut_rom_informal = serializers.CharField(read_only=True, allow_null=True)
    bhut_script_formal = serializers.CharField(read_only=True, allow_null=True)
    bhut_script_informal = serializers.CharField(
        read_only=True, allow_null=True)
    bhutia_source = serializers.CharField(read_only=True, allow_null=True)

    # example = serializers.CharField(read_only=True, allow_null=True)
    # spoken_b = serializers.CharField(read_only=True, allow_null=True)
    # spoken_e = serializers.CharField(read_only=True, allow_null=True)


class EnglishSerializer(serializers.Serializer):
    entry_id = serializers.IntegerField(read_only=True)
    word = serializers.CharField(read_only=True, allow_null=True)
    definition = serializers.CharField(read_only=True, allow_null=True)


