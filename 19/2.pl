#!/usr/bin/perl

use warnings;
use strict;
use feature 'say';

use Time::HiRes qw(usleep nanosleep);

my @patterns = reverse sort <> =~ /(\w+)/g;
<>;

my $re = join '|', @patterns;


my $total = 0;
while (<>) {
    chomp;
    next unless /^(?:$re)+$/o;
    my $count = count($_);
    $total+=$count;
}
say $total;

my %cache=();
my $counter = 0;
sub count {
    my $str = shift;
    my $cached = $cache{$str};
    return $cached if defined $cached;
    my $acc = shift;
    return $acc unless $str;
    my $count = 0;
    for my $pattern (@patterns) {
        my $len = length $pattern;
        my $prefix = substr($str, 0, $len);
        next unless $pattern eq $prefix;
        my $suffix = substr($str, $len);
        $count += count($suffix, 1);
    }
    $cache{$str} = $count;
    return $count;

}
