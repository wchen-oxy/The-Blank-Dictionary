from django.shortcuts import render

from abc import ABC, abstractmethod
from typing import List
from .strategy import Strategy



class SearchContext(ABC):
    #This is actually the interface
    def __init__(self, strategy: Strategy) -> None:
        """
        Usually, the Context accepts a strategy through the constructor, but
        also provides a setter to change it at runtime.
        """
        print("newly created")

        self._strategy = strategy

    @property
    def strategy(self) -> Strategy:
        """
        The Context maintains a reference to one of the Strategy objects. The
        Context does not know the concrete class of a strategy. It should work
        with all strategies via the Strategy interface.
        """
        return self._strategy

    @strategy.setter
    def strategy(self, strategy: Strategy) -> None:
        """
        Usually, the Context allows replacing a Strategy object at runtime.
        """
        self._strategy = strategy

    def execute_strategy(self, request, lang, translation, optional_id=None):
        print(translation)
        return self._strategy.execute(request, lang, translation,  optional_id)

                