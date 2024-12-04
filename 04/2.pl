#!/usr/bin/perl -w

use warnings;
use strict;
use feature 'say';



local $/;

my $input = <>;

my ($firstline) = $input =~ /^(\S+)/;
my $len = length($firstline) - 1;
my $s = "(?:.|\\n){$len}";

my @regexes = ( qr/M(?=.S${s}A${s}M.S)/, qr/M(?=.M${s}A${s}S.S)/,
                qr/S(?=.M${s}A${s}S.M)/, qr/S(?=.S${s}A${s}M.M)/
              );

my $total = 0;

for my $regex (@regexes) {
    my @matches = $input =~ /($regex)/g;
    $total += @matches;
}

say $total;
