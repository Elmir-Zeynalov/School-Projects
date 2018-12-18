Script for a Syntax Highlighter.

5.1 
Has the highlighter.py file and will run if you just type in > python3 highlighter.py
it will use the python.syntax and python.theme files to color the demo.py file(which is a fibonacci file i just found online). It works rather well but i haven't been able to print  out "\n" in the terminal so the code will actually apply the newline and will skip a line in the terminal.

5.2
Is my implementation of the python syntax, the specific parts i choose to highlight are specified in the file pythonSyntaxList and the demo file is again demo.py(fibonacci).

5.3
Same as above but this time i choose to color java code. file used as demo is Oblig.java.Pretty content with this one but i wish i could color class names and references that start with capital letters(feks. Random random).

5.5 - 5.6
Finished these two and think that they work pretty well. The code seems to notice lines that were added/removed and the diff.syntax diff.theme work with the highlight.py. If you have 2 files (original and modified) you can run 
> python3 diff.py original modified.txt output.txt
then the differences will be stored in output.txt and you can get coolers via highlighter.py diff.syntax diff.theme output.txt
