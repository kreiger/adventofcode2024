#!/usr/bin/perl -w

use warnings;
use strict;
use feature 'say';



local $/;

my $input = <>;

my ($firstline) = $input =~ /^(\S+)/;
my $len = length($firstline) - 1;
my $s0 = "(?:.|\\n){$len}";
$len++;
my $s1 = "(?:.|\\n){$len}";
$len++;
my $s2 = "(?:.|\\n){$len}";

my @regexes = ( "XMAS", "SAMX",
                "X(?=${s0}M${s0}A${s0}S)", "S(?=${s0}A${s0}M${s0}X)",
                "X(?=${s1}M${s1}A${s1}S)", "S(?=${s1}A${s1}M${s1}X)",
                "X(?=${s2}M${s2}A${s2}S)", "S(?=${s2}A${s2}M${s2}X)"
              );

my $total = 0;

for my $regex (@regexes) {
    my @matches = $input =~ /($regex)/g;
    $total += @matches;
}

say $total;

