package Vector2D;

use Exporter 'import';
our @EXPORT = qw( V );

use overload 
    '+'  => \&add,
    '-' =>  \&subtract,
    '==' => \&eq,
    '""' => sub { my $v = shift; "($v->[0] $v->[1])" }
;

sub new {
    my $class = shift;
    my ($x, $y) = @_;

    my $self = bless [$x,$y], $class;

    return $self;
}

sub add {
    my $v = shift;
    my $w = shift;
    return V($v[0] + $w[0], $v[1] + $w[1]);
}

sub subtract {
    my $v = shift;
    my $w = shift;
    return V($v[0] - $w[0], $v[1] - $w[1]);
}

sub eq {
    my $v = shift;
    my $w = shift;
    print $v, $w, "\n";
    return $v[0] == $w[0] && $v[1] == $w[1];
}

sub V {
    my ($x, $y) = @_;
    return Vector2D->new($x, $y);
}

1;