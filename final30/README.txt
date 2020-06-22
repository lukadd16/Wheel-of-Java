Come back to this assignment at some point just for own purposes.

Create a more thought out OOP design, think about what things need to be objects such as the wheel object that has the three lists as class variables and the spinWheel as a method.

On the right track with the contestants, just make the comptuer output it's choice of move the same as the human does (I already partially check what it wants to do anyways), will need to tweak CPU's logic a bit, add a list of consonants+vowels that are in an order of most common to least common (maybe) so as to make the letter guess more genius (because as it stands, genius only means it is good at guessing the correct phrase, not necessarily at making smart guesses).

Essentially, all of the methods that are in the main class should be in their own object (aside from the print dash lines one maybe), then all that needs to be done is call each appropriate method with a few loops and comparisons here and there, but no major blocks of code.

Also allow the user to pick how many contestants there will be (both human and computer seperately, repeatedly ask them), and instantiate the objects as appropriate.  Would also need to tweak endgame winner checks/resetting as those are hard coded.

On the note of hard coded, make hard coded values as minimal as possible, character used for separating the letters + words on the board? Make those class variables of the board object, etc.  # of rounds is another example.

Then, in far future, consider having the toss up rounds (but then it would need to be asynchronous) + prizes + winner moves onto the final round.

Dad Ideas:
- Convert to proper object structure
- Look into "cases" rather than many many if else statements
- Have one central file/location where constants, or symbol used to separate the letters on the board are stored
- Look into smarter AI (train one to recognize words?)
- Make a GUI version or host it some other way online