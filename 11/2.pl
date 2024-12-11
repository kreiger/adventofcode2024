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

my %cache = ();

sub recurse {
    my ($depth, $stone) = @_;
    return 1 unless $depth--;

    return $cache{$depth}{$stone} ||= recurse2($depth, $stone);
}

sub recurse2{
    my ($depth, $stone) = @_;
    return recurse($depth, 1) if $stone == 0;

    my $len = length $stone;
    return recurse($depth, 2024 * $stone) if $len & 1;

    my $half = $len >> 1;
    my $l = int substr($stone, 0, $half);
    my $r = int substr($stone, $half);
    return recurse($depth, $l) + recurse($depth, $r);
}

my $total = 0;
for (@input) {
    my $count = recurse($iterations, $_);
    $total += $count;
    say "$_: $count";
}
say $total;
