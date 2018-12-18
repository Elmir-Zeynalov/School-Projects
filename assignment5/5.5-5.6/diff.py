#!/usr/bin/env python3

import sys
from numpy import zeros

class Superdiff:

    def __init__(self, original_file, modified_file, output_file=None):
        """Constructor
        Args:
            original_file: The filename of the original file to be compared.
            modified_file: The filename of the modified file
            output_file: The filename of an [optional] output file.
        """
        self.original_file = original_file
        self.modified_file = modified_file
        self.output_file = output_file
        self.output = [] # An empty list used to store diff-lines

    def __call__(self):
        """Calling functions for Superdiff
        """
        original = self._read_from_file(original_file)
        modified = self._read_from_file(modified_file)
        subsequence = self._longest_common_subsequence(original, modified)
        self._generate_output(subsequence, original, modified)
        # Check if the result should be written to file or stdout
        if self.output_file:
            self._write_to_file(self.output_file)
        else:
            self._output_to_screen(self.output)

    def _write_to_file(self, filename):
        """ This function writes data(that is stored in list [self.output]) to the output-file.
        Args:
            filename: name of the output-file.
        Return: -void
        """
        with open(filename, 'w') as f:
            for i in self.output:
                f.write("%s\n" % i)

    def _output_to_screen(self, output):
        """Displays data to terminal screen.
        Args:
            output: The output to be printed.
        """
        for i in output:
            print(i)

    def _longest_common_subsequence(self, original, modified):
        """
        This function calculates the longest common subsequence from the
        lines in the original and modified file.
        The function compares two lines against one another. If they are
        equal the subsequence matrix at the indices of these lines gets
        incremented by one. If not, it stores the largest value of the
        neighbour to the left (i-1, j) or the neighbour above (i, j-1).
        Args:
            original: A list with the lines in the original file.
            modified: A list with the lines in the modified file.
        Returns:
            array: A 2D-array with the longest common subsequence.
        """
        subsequence = zeros((len(original)+1, len(modified)+1))
        for i in range(1, len(original)+1):
            for j in range(1, len(modified)+1):
                if original[i-1] == modified[j-1]:
                    subsequence[i][j] = subsequence[i-1][j-1] + 1
                else:
                    subsequence[i][j] = max(subsequence[i][j-1], subsequence[i-1][j])

        return subsequence

    def _generate_output(self, subsequence, original, modified):
        """Function used to generate output to be written to screen.
        First checks if two lines are equal. If so, then there are
        no changes and we store the result whilst decrementing the
        indecies of the original and the modified line.
        If the two lines are unequal, the function tries to find the path
        of the largest subsequence.

        -- If it moves to the left (i-1, j)-> we have removed a line.
        ++ If it moves up (i, j-1) -> we have added a line.

        Args:
            subsequence: A 2D-array with the values of the longest common
                         subsequences.
            original: A list with the lines in the original file.
            modified: A list with the lines in the modified file.
        """
        i = len(original)
        j = len(modified)
        while i > 0 and j > 0:
            if original[i-1] == modified[j-1]:
                self.output.append("0 %s" % original[i-1])
                i -= 1; j -= 1
            elif subsequence[i-1][j] > subsequence[i][j-1]:
                self.output.append("- %s" % original[i-1])
                i -= 1
            else:
                self.output.append("+ %s" % modified[j-1])
                j -= 1

        # Find the final removed lines first
        while i > 0:
            if j == 0 or subsequence[i][j-1] < subsequence[i-1][j]:
                self.output.append("- %s" % original[i-1])
            i -= 1

        # Find the final added lines last
        while j > 0:
            if i == 0 or subsequence[i-1][j-1] >= subsequence[i][j-1]:
                self.output.append("+ %s" % modified[j-1])
            j -= 1

        # Reverse the direction of self.output as the values are added
        # backwards in the loops.
        self.output = reversed(self.output)

    def _read_from_file(self, filename):
        """Reads the contents of a file
        This function strips the lines for new-lines and adds all lines
        into a list.
        Args:
            filename: The name of the file to be read.
        Return:
            list: A list with the new-line separated lines in the file.
        """
        try:
            with open(filename, 'r', encoding="utf8") as f:
                return [line.strip('\n') for line in f]
        except FileNotFoundError :
            print ("Code file not found")
            sys.exit(1)

if __name__ == '__main__':
    from sys import argv, exit
    try:
        original_file = argv[1]
        modified_file = argv[2]
    except IndexError:
        print("Usage: %s <original file> <modified file> <output file> (optional)" % argv[0])
        exit(1)
    if len(argv) == 4:
        sd = Superdiff(original_file, modified_file, argv[3])
    else:
        sd = Superdiff(original_file, modified_file)
    sd()