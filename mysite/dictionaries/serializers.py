from rest_framework import serializers
from dictionaries.models import Bhutia

class BhutiaSerializer(serializers.Serializer):
    # id = serializers.IntegerField(read_only=True)
    romanization = serializers.CharField(read_only=True, allow_null=True)
    ipa = serializers.CharField(read_only=True, allow_null=True)
    category = serializers.CharField(read_only=True, allow_null=True)
    eng_trans = serializers.CharField(read_only=True, allow_null=True)
    tib_script = serializers.CharField(read_only=True, allow_null=True)
    example = serializers.CharField(read_only=True, allow_null=True)

