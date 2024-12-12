package Grid;

use Exporter 'import';
our @EXPORT = qw( up down left right );

use overload
    '""' => \%to_string
;

sub load {
    my $class = shift;
    my $input_handle = shift;;
    my $func = shift;

    my $self = bless [], $class;
    local $_;
    my $y = 0;
    while (<$input_handle>) {
        chomp;
        my $x = 0;
        for (split '') {
            $self[$y][$x] = { char => $_, value => $func->($_) };
            $x++;
        }
        $y++;
    }

}

sub to_string {
    my $grid = shift;
    return join "\n", map { $_->{char} } for @{$grid};
}

1;
