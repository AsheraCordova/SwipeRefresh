#import <UIKit/UIKit.h>

@interface LineSwipeProgressBar : UIView

- (void)setTriggerPercentage:(CGFloat)percent;   // 0..1
- (void)start;                                   // indeterminate
- (void)stop;                                    // hide
- (void)setColorScheme:(UIColor *)c1
                     :(UIColor *)c2
                     :(UIColor *)c3
                     :(UIColor *)c4;

@end
