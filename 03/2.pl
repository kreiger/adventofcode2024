#!/usr/bin/perl -w

use warnings;
use strict;
use feature 'say';
use List::Util qw( zip );


my $total = 0;
my $on = 1;
while (<>) {
    while (/mul\((\d+),(\d+)\)|(do)\(\)|(don)\'t\(\)/g) {
        if ($3) {
            $on = 1;
        }
        elsif ($4) {
            $on = 0;
        }
        elsif ($on) {
            $total += $1 * $2;
        }
    }

}

say $total;
