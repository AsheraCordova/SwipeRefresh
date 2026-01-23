// start - imports

export const enum RefreshMode {
swipe_mode = "swipe_mode",
pull_mode = "pull_mode",
}	
import CommandAttr from '../../widget/CommandAttr';
import IWidget from '../../widget/IWidget';
import ILayoutParam from '../../widget/ILayoutParam';
import {plainToClass, Type, Exclude, Expose, Transform} from "class-transformer";
import {Gravity} from '../../widget/TypeConstants';
import {ITranform, TransformerFactory} from '../../widget/TransformerFactory';
import {Event} from '../../app/Event';
import {MotionEvent} from '../../app/MotionEvent';
import {DragEvent} from '../../app/DragEvent';
import {KeyEvent} from '../../app/KeyEvent';
import { ScopedObject } from '../../app/ScopedObject';
import { Mixin, decorate } from 'ts-mixer';















import {ViewGroupImpl_LayoutParams} from './ViewGroupImpl';

// end - imports
import {ViewGroupImpl} from './ViewGroupImpl';
export abstract class CustomSwipeRefreshLayoutImpl<T> extends ViewGroupImpl<T>{
	//start - body
	static initialize() {
    }	
	@decorate(Type(() => CommandAttr))
	@decorate(Expose({ name: "customHeadLayout" }))
	customHeadLayout!:CommandAttr<string>| undefined;
	@decorate(Type(() => CommandAttr))
	@decorate(Expose({ name: "refresh_mode" }))
	refresh_mode!:CommandAttr<RefreshMode>| undefined;
	@decorate(Type(() => CommandAttr))
	@decorate(Expose({ name: "onRefresh" }))
	onRefresh!:CommandAttr<string>| undefined;
	@decorate(Type(() => CommandAttr))
	@decorate(Expose({ name: "enableHorizontalScroll" }))
	enableHorizontalScroll!:CommandAttr<boolean>| undefined;
	@decorate(Type(() => CommandAttr))
	@decorate(Expose({ name: "enable_top_progress_bar" }))
	enable_top_progress_bar_!:CommandAttr<boolean>| undefined;
	@decorate(Type(() => CommandAttr))
	@decorate(Expose({ name: "keep_refresh_head" }))
	keep_refresh_head!:CommandAttr<boolean>| undefined;
	@decorate(Type(() => CommandAttr))
	@decorate(Expose({ name: "time_out_return_to_top" }))
	time_out_return_to_top!:CommandAttr<number>| undefined;
	@decorate(Type(() => CommandAttr))
	@decorate(Expose({ name: "time_out_refresh_complete" }))
	time_out_refresh_complete!:CommandAttr<number>| undefined;
	@decorate(Type(() => CommandAttr))
	@decorate(Expose({ name: "return_to_top_duration" }))
	return_to_top_duration!:CommandAttr<number>| undefined;
	@decorate(Type(() => CommandAttr))
	@decorate(Expose({ name: "return_to_header_duration" }))
	return_to_header_duration!:CommandAttr<number>| undefined;
	@decorate(Type(() => CommandAttr))
	@decorate(Expose({ name: "resistanceFactor" }))
	resistanceFactor!:CommandAttr<number>| undefined;
	@decorate(Type(() => CommandAttr))
	@decorate(Expose({ name: "progressBarHeight" }))
	progressBarHeight!:CommandAttr<string>| undefined;
	@decorate(Type(() => CommandAttr))
	@decorate(Expose({ name: "triggerDistance" }))
	triggerDistance!:CommandAttr<number>| undefined;
	@decorate(Type(() => CommandAttr))
	@decorate(Expose({ name: "top_progress_bar_color_scheme" }))
	top_progress_bar_color_scheme!:CommandAttr<string>| undefined;

	@decorate(Exclude())
	protected thisPointer: T;	
	protected abstract getThisPointer(): T;
	reset() : T {	
		super.reset();
		this.customHeadLayout = undefined;
		this.refresh_mode = undefined;
		this.onRefresh = undefined;
		this.enableHorizontalScroll = undefined;
		this.enable_top_progress_bar_ = undefined;
		this.keep_refresh_head = undefined;
		this.time_out_return_to_top = undefined;
		this.time_out_refresh_complete = undefined;
		this.return_to_top_duration = undefined;
		this.return_to_header_duration = undefined;
		this.resistanceFactor = undefined;
		this.progressBarHeight = undefined;
		this.triggerDistance = undefined;
		this.top_progress_bar_color_scheme = undefined;
		return this.thisPointer;
	}
	constructor(id: string, path: string[], event:  string) {
		super(id, path, event);
		this.thisPointer = this.getThisPointer();
	}
	

	public setCustomHeadLayout(value : string) : T {
		this.resetIfRequired();
		if (this.customHeadLayout == null || this.customHeadLayout == undefined) {
			this.customHeadLayout = new CommandAttr<string>();
		}
		
		this.customHeadLayout.setSetter(true);
		this.customHeadLayout.setValue(value);
		this.orderSet++;
		this.customHeadLayout.setOrderSet(this.orderSet);
		return this.thisPointer;
	}
		

	public setRefresh_mode(value : RefreshMode) : T {
		this.resetIfRequired();
		if (this.refresh_mode == null || this.refresh_mode == undefined) {
			this.refresh_mode = new CommandAttr<RefreshMode>();
		}
		
		this.refresh_mode.setSetter(true);
		this.refresh_mode.setValue(value);
		this.orderSet++;
		this.refresh_mode.setOrderSet(this.orderSet);
		return this.thisPointer;
	}
		

	public setOnRefresh(value : string) : T {
		this.resetIfRequired();
		if (this.onRefresh == null || this.onRefresh == undefined) {
			this.onRefresh = new CommandAttr<string>();
		}
		
		this.onRefresh.setSetter(true);
		this.onRefresh.setValue(value);
		this.orderSet++;
		this.onRefresh.setOrderSet(this.orderSet);
		return this.thisPointer;
	}
		

	public setEnableHorizontalScroll(value : boolean) : T {
		this.resetIfRequired();
		if (this.enableHorizontalScroll == null || this.enableHorizontalScroll == undefined) {
			this.enableHorizontalScroll = new CommandAttr<boolean>();
		}
		
		this.enableHorizontalScroll.setSetter(true);
		this.enableHorizontalScroll.setValue(value);
		this.orderSet++;
		this.enableHorizontalScroll.setOrderSet(this.orderSet);
		return this.thisPointer;
	}
		

	public enable_top_progress_bar(value : boolean) : T {
		this.resetIfRequired();
		if (this.enable_top_progress_bar_ == null || this.enable_top_progress_bar_ == undefined) {
			this.enable_top_progress_bar_ = new CommandAttr<boolean>();
		}
		
		this.enable_top_progress_bar_.setSetter(true);
		this.enable_top_progress_bar_.setValue(value);
		this.orderSet++;
		this.enable_top_progress_bar_.setOrderSet(this.orderSet);
		return this.thisPointer;
	}
		

	public setKeep_refresh_head(value : boolean) : T {
		this.resetIfRequired();
		if (this.keep_refresh_head == null || this.keep_refresh_head == undefined) {
			this.keep_refresh_head = new CommandAttr<boolean>();
		}
		
		this.keep_refresh_head.setSetter(true);
		this.keep_refresh_head.setValue(value);
		this.orderSet++;
		this.keep_refresh_head.setOrderSet(this.orderSet);
		return this.thisPointer;
	}
		

	public setTime_out_return_to_top(value : number) : T {
		this.resetIfRequired();
		if (this.time_out_return_to_top == null || this.time_out_return_to_top == undefined) {
			this.time_out_return_to_top = new CommandAttr<number>();
		}
		
		this.time_out_return_to_top.setSetter(true);
		this.time_out_return_to_top.setValue(value);
		this.orderSet++;
		this.time_out_return_to_top.setOrderSet(this.orderSet);
		return this.thisPointer;
	}
		

	public setTime_out_refresh_complete(value : number) : T {
		this.resetIfRequired();
		if (this.time_out_refresh_complete == null || this.time_out_refresh_complete == undefined) {
			this.time_out_refresh_complete = new CommandAttr<number>();
		}
		
		this.time_out_refresh_complete.setSetter(true);
		this.time_out_refresh_complete.setValue(value);
		this.orderSet++;
		this.time_out_refresh_complete.setOrderSet(this.orderSet);
		return this.thisPointer;
	}
		

	public setReturn_to_top_duration(value : number) : T {
		this.resetIfRequired();
		if (this.return_to_top_duration == null || this.return_to_top_duration == undefined) {
			this.return_to_top_duration = new CommandAttr<number>();
		}
		
		this.return_to_top_duration.setSetter(true);
		this.return_to_top_duration.setValue(value);
		this.orderSet++;
		this.return_to_top_duration.setOrderSet(this.orderSet);
		return this.thisPointer;
	}
		

	public setReturn_to_header_duration(value : number) : T {
		this.resetIfRequired();
		if (this.return_to_header_duration == null || this.return_to_header_duration == undefined) {
			this.return_to_header_duration = new CommandAttr<number>();
		}
		
		this.return_to_header_duration.setSetter(true);
		this.return_to_header_duration.setValue(value);
		this.orderSet++;
		this.return_to_header_duration.setOrderSet(this.orderSet);
		return this.thisPointer;
	}
		

	public setResistanceFactor(value : number) : T {
		this.resetIfRequired();
		if (this.resistanceFactor == null || this.resistanceFactor == undefined) {
			this.resistanceFactor = new CommandAttr<number>();
		}
		
		this.resistanceFactor.setSetter(true);
		this.resistanceFactor.setValue(value);
		this.orderSet++;
		this.resistanceFactor.setOrderSet(this.orderSet);
		return this.thisPointer;
	}
		

	public setProgressBarHeight(value : string) : T {
		this.resetIfRequired();
		if (this.progressBarHeight == null || this.progressBarHeight == undefined) {
			this.progressBarHeight = new CommandAttr<string>();
		}
		
		this.progressBarHeight.setSetter(true);
		this.progressBarHeight.setValue(value);
		this.orderSet++;
		this.progressBarHeight.setOrderSet(this.orderSet);
		return this.thisPointer;
	}
		

	public setTriggerDistance(value : number) : T {
		this.resetIfRequired();
		if (this.triggerDistance == null || this.triggerDistance == undefined) {
			this.triggerDistance = new CommandAttr<number>();
		}
		
		this.triggerDistance.setSetter(true);
		this.triggerDistance.setValue(value);
		this.orderSet++;
		this.triggerDistance.setOrderSet(this.orderSet);
		return this.thisPointer;
	}
		

	public setTop_progress_bar_color_scheme(value : string) : T {
		this.resetIfRequired();
		if (this.top_progress_bar_color_scheme == null || this.top_progress_bar_color_scheme == undefined) {
			this.top_progress_bar_color_scheme = new CommandAttr<string>();
		}
		
		this.top_progress_bar_color_scheme.setSetter(true);
		this.top_progress_bar_color_scheme.setValue(value);
		this.orderSet++;
		this.top_progress_bar_color_scheme.setOrderSet(this.orderSet);
		return this.thisPointer;
	}
		
	//end - body

}
	
//start - staticinit

export class CustomSwipeRefreshLayout extends CustomSwipeRefreshLayoutImpl<CustomSwipeRefreshLayout> implements IWidget{
    getThisPointer(): CustomSwipeRefreshLayout {
        return this;
    }
    
   	public getClass() {
		return CustomSwipeRefreshLayout;
	}
	
   	constructor(id: string, path: string[], event: string) {
		super(id, path, event);	
	}
}

CustomSwipeRefreshLayoutImpl.initialize();
export interface OnRefreshEvent extends Event{
        //:;


}

//end - staticinit
