#!/usr/bin/perl

use warnings;
use strict;

use feature 'say';

use List::Util 'max';

my %changevalues = ();
while (<>) {

    my ($seed) = /^(\d+)/;
    my $num = $seed;
    my $ones = $num % 10;
    my @changes = ();
    my %cv = ();
    for (my $i = 0; $i < 2000; $i++) {
        $num ^= $num << 6;
        $num &= 16777215;
        $num ^= $num >> 5;
        $num &= 16777215;
        $num ^= $num << 11;
        $num &= 16777215;
        my $newones = $num % 10;
        my $change = $newones - $ones;
        $ones = $newones;
        push @changes, $change;
        if (4 == @changes) {
            my $key = join ',', @changes;
            $changevalues{$key} += $newones unless $cv{$key}++;
            shift @changes;
        }
    }
}

say max values %changevalues;

