#import "LineSwipeProgressBar.h"

static CGFloat const kBarHeight = 4.0;
static NSTimeInterval const kAnimDuration = 2.0;

@interface LineSwipeProgressBar ()
@property (nonatomic, strong) CAGradientLayer *gradient;
@end

@implementation LineSwipeProgressBar

- (instancetype)init {
    CGRect frame = CGRectMake(0, 0,
                              UIScreen.mainScreen.bounds.size.width,
                              kBarHeight);
    self = [super initWithFrame:frame];
    if (self) {
        [self commonInit];
    }
    return self;
}

- (void)commonInit {
    self.hidden = YES;
    self.userInteractionEnabled = NO;

    _gradient = [CAGradientLayer layer];
    _gradient.frame = self.bounds;
    _gradient.startPoint = CGPointMake(0, 0.5);
    _gradient.endPoint   = CGPointMake(1, 0.5);

    [self setColorScheme:
        [UIColor colorWithRed:0 green:0 blue:0 alpha:1]
        		:[UIColor colorWithRed:1 green:1 blue:1 alpha:1]
        		:[UIColor colorWithRed:0 green:0 blue:0 alpha:1]
        		:[UIColor colorWithRed:1 green:1 blue:1 alpha:1] 
    ];

    [self.layer addSublayer:_gradient];
}

#pragma mark - API

- (void)setTriggerPercentage:(CGFloat)percent {
    percent = MAX(0, MIN(1, percent));

    [self.gradient removeAllAnimations];
    self.hidden = NO;

    CGFloat width = self.superview.bounds.size.width * percent;
    self.gradient.frame = CGRectMake(
        (self.superview.bounds.size.width - width) / 2,
        0,
        width,
        kBarHeight
    );
}

- (void)start {
    self.hidden = NO;

    CGFloat w = self.superview.bounds.size.width;
    self.gradient.frame = CGRectMake(-w, 0, w * 2, kBarHeight);

    CABasicAnimation *anim = [CABasicAnimation animationWithKeyPath:@"transform.translation.x"];
    anim.fromValue = @(-w * 0.75);
    anim.toValue   = @(w * 0.75);
    anim.duration  = kAnimDuration;
    anim.repeatCount = HUGE_VALF;
    anim.timingFunction =
        [CAMediaTimingFunction functionWithName:kCAMediaTimingFunctionEaseInEaseOut];

    [self.gradient addAnimation:anim forKey:@"swipe"];
}

- (void)stop {
    [self.gradient removeAllAnimations];
    self.hidden = YES;
}

- (void)setColorScheme:(UIColor *)c1
                     :(UIColor *)c2
                     :(UIColor *)c3
                     :(UIColor *)c4 {
    self.gradient.colors = @[
        (__bridge id)c1.CGColor,
        (__bridge id)c2.CGColor,
        (__bridge id)c3.CGColor,
        (__bridge id)c4.CGColor
    ];
}

#pragma mark - Layout

- (void)didMoveToSuperview {
    [super didMoveToSuperview];
    if (self.superview) {
        CGRect f = self.frame;
        f.size.width = self.superview.bounds.size.width;
        self.frame = f;
        self.gradient.frame = self.bounds;
    }
}

@end
