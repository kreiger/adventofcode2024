#!/usr/bin/perl

use warnings;
use strict;
use feature 'say';

local $/ = "";

my $total = 0;
while (<>) {
    chomp;
    s/^\D+//;
    my ($x1, $y1, $x2, $y2, $x, $y) = split /\D+/;
    $x += 10000000000000;
    $y += 10000000000000;

#    my $matrix = [[$x1, $x2], 
#                  [$y1, $y2]];
    my $det = $x1*$y2 - $x2*$y1;
    next if 0 == $det;
    #say $det;
    my $a = ($x * $y2 - $y * $x2) / $det;
    my $b = ($y * $x1 - $x * $y1) / $det;

    $total += 3*$a + $b if int($a) == $a and int($b) == $b;

#    say "$a * $b1 + $b * $b2 = $price";

}

say $total;

