#!/usr/bin/perl -w

use warnings;
use strict;
use feature 'say';

use Data::Dumper;

use lib '.';

use Vector2D;

my %numpad = (7 => V(0, -3), 8 => V(1, -3), 9 => V(2, -3),
    4           => V(0, -2), 5 => V(1, -2), 6 => V(2, -2),
    1           => V(0, -1), 2 => V(1, -1), 3 => V(2, -1),
    0           => V(1, 0), A => V(2, 0));

my %dirpad = ('^' => V(1, 0), A => V(2, 0),
    '<'           => V(0, 1), 'v' => V(1, 1), '>' => V(2, 1));


#print Dumper(\%numpad, \%dirpad);

my $r0 = { pos => V(2, 0) };
my $r1 = { pos => V(2, 0) };
my $r2 = { pos => V(2, 0) };

my $total = 0;
while (<>) {
    chomp;
    my ($c0) = /^(\w+)/;
    say $c0;
    my @c1 = code($c0, \%numpad, $r0);
    my $c1 = join '', @c1;
    say join ' ', @c1;
    my @c2 = code($c1, \%dirpad, $r1);
    my $c2 = join '', @c2;
    say join ' ', @c2;
    my @c3 = code($c2, \%dirpad, $r2);
    say join ' ', @c3;
    my $len = length join '', @c3;
    my $num = int substr $c0, 0, 3;
    say $len;
    say $num;
    my $complexity = $num * $len;
    say $complexity;
    $total += $complexity;
    print "\n";
}

say $total;

sub code {
    my ($code, $pad, $robot) = @_;
    my @res = ();
    for my $digit (split '', $code) {
        push @res, digit($digit, $pad, $robot);
    }
    return @res;
}

sub digit {
    my ($digit, $pad, $robot) = @_;
    my @res = ();
    my $w0 = $pad->{$digit};
    my $pos = $robot->{pos};
    my $d0 = $w0 - $pos;
    my $x0 = $d0->x;
    my $y0 = $d0->y;
    if ($w0->y == 0 and $pos->y == 0) {
        while ($y0 < 0) {
            push @res, '^';
            $y0++;
        }
        while ($y0 > 0) {
            push @res, 'v';
            $y0--;
        }
    }
    while ($x0 > 0) {
        push @res, '>';
        $x0--;
    }
    while ($x0 < 0) {
        push @res, '<';
        $x0++;
    }
    while ($y0 < 0) {
        push @res, '^';
        $y0++;
    }
    while ($y0 > 0) {
        push @res, 'v';
        $y0--;
    }
    $robot->{pos} = $w0;
    push @res, "A";

    return join '', @res;
}

