#!/usr/bin/perl

use warnings;
use strict;
use integer;
use feature 'say';
use Data::Dumper;

local $/;

my $iterations = shift;
my @input = <> =~ /(\d+)/g;

say "0: @input";

my $total = 0;
for (my $s = 0; $s < @input; ++$s) {
    my @stones = $input[$s];
    say "Starting stone $s";
    for my $i (1..$iterations) {
        my @new = ();
        for my $stone (@stones) {
            if ($stone == 0) {
                push @new, 1;
                next;
            }
            my $len = length $stone;
            if ($len & 1) {
                push @new, 2024 * $stone;
                next;
            }

            my $half = $len >> 1;
            push @new, int substr($stone, 0, $half);
            push @new, int substr($stone, $half);
        }
        @stones = @new;
        say "$s, $i";
    }
    $total += @stones;
}

say $total;
