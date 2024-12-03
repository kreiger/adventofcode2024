#!/usr/bin/perl -w

use warnings;
use strict;
use feature 'say';
use List::Util qw( zip );


my $total = 0;

while (<>) {

    while (/mul\((\d+),(\d+)\)/g) {

        $total += $1 * $2;

    }

}

say $total;
