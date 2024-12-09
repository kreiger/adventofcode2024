#!/usr/bin/perl

use warnings;
use strict;
use feature 'say';
use Data::Dumper;

local $/;

my $input = <>;

my $id = 0;
my $free = 0;
my @blocks = ();

while ($input =~ /(\d)/g) {
    my $length = $1;
    my $id = $free ? '' : $id++;
    push @blocks, $id for 1..$length;
    $free = !$free;
}

my $checksum = 0;
my $pos = 0;
for my $block (@blocks) {
    while ($block eq '') {
        $block = pop @blocks;
    }
    $checksum += $pos++ * $block;
}

say $checksum;
