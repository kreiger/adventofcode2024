#!/usr/bin/perl

use warnings;
use strict;

use feature 'say';

my $total = 0;
while (<>) {

    my ($seed) = /^(\d+)/;
    my $num = $seed;
    for (my $i = 0; $i < 2000; $i++) {
        $num ^= $num << 6;
        $num &= 16777215;
        $num ^= $num >> 5;
        $num &= 16777215;
        $num ^= $num << 11;
        $num &= 16777215;
    }
    say "$seed: $num";
    $total += $num;
}

say $total;