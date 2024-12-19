#!/usr/bin/perl

use warnings;
use strict;
use feature 'say';

my @patterns = <> =~ /(\w+)/g;
<>;

my $re = join '|', @patterns;

my $total = 0;
while (<>) {
    chomp;
    $total++ if /^(?:$re)+$/o;
}
say $total;
