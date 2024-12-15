package Vector2D;

use strict;
use warnings;
use Exporter 'import';
our @EXPORT = qw( V );

use overload 
    '+'  => \&add,
    '-' =>  \&subtract,
    '*' =>  \&multiply,
    'neg' => sub { subtract(V(0,0), shift); } ,
    '==' => \&eq,
    '""' => sub { my $v = shift; "($v->[0] $v->[1])" }
;


sub new {
    my $class = shift;
    my ($x, $y) = @_;

    my $self = bless [$x,$y], $class;

    return $self;
}

sub V {
    my ($x, $y) = @_;
    return Vector2D->new($x, $y);
}

sub x {
    my $v = shift;
    return $v->[0];
}

sub y {
    my $v = shift;
    return $v->[1];
}

sub add {
    my ($v, $w) = @_;
    my $r = V($v->[0] + $w->[0], $v->[1] + $w->[1]);
    return $r;
}

sub subtract {
    my $v = shift;
    my $w = shift;
    return V($v->[0] - $w->[0], $v->[1] - $w->[1]);
}

sub multiply {
    my $v = shift;
    my $s = shift;
    return V($v->[0] * $s, $v->[1] * $s);    
}

sub eq {
    my $v = shift;
    my $w = shift;
    return $v->[0] == $w->[0] && $v->[1] == $w->[1];
}


1;