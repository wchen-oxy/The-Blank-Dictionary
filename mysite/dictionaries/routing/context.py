
class SearchContext():
    #This is actually the interface
    def __init__(self, strategy: Strategy) -> None:
        self.strategy = strategy
