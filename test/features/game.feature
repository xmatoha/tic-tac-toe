Feature: Game rules
    Scenario: Game initiation
        Given I am a user
        When I start the game board
        Then I want to see empty board
        And I want player X to be starting player
        And I want to have all possible moves
        And the game should not be in end state

    Scenario: Game rules - player turns
        Given I am a user X
        When I make a turn
        Then user O turn follows

    Scenario: Game rules - use only empty
        Given some valid game state
        And cell 4 is empty
        When user X makes a move to cell 4
        Then cell 4 should be occupied by user X
        
    Scenario: Game rules - invalid if not empty
        Given some valid game state
        And cell 4 is occupied by player O
        When user X makes a move to cell 4
        Then game status should indicate invalid move


    Scenario: Game endings - win vertically
        Given board looks like [["X", " ", " "], ["X", "O", " "], [" ", " ", "O"]]
        When user X makes a turn to grid position [2, 0]
        Then X wins


    Scenario: Game endings - win horizontally
        Given board looks like [["O", " ", "O"], ["X", " ", "X"], ["O", " ", " "]]
        When user X makes a turn to grid position [1, 1]
        Then X wins

    Scenario: Game endings - win diagonally negative
        Given board looks like [["O", " ", "X"], ["X", " ", " "], ["X", " ", " "]]
        When user X makes a turn to grid position [1, 1]
        Then X wins

    Scenario: Game endings - win diagonally positive
        Given board looks like [["X", " ", "O"], ["O", "X", " "], ["O", " ", " "]]
        When user X makes a turn to grid position [2, 2]
        Then X wins

    Scenario: Game endings - win at last turn
        Given board looks like [["O", "X", "O"], ["X", "X", " "], ["O", "O", "X"]]
        When user X makes a turn to grid position [1, 2]
        Then X wins

    Scenario: Game endings - draw
        Given board looks like [["X", "O", "X"], ["O", "O", " "], ["X", "X", "O"]]
        When user X makes a turn to grid position [1, 2]
        Then it is a draw
