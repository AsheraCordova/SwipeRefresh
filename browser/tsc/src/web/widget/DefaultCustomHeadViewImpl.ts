// start - imports

export const enum Divider {
none = "none",
beginning = "beginning",
end = "end",
middle = "middle",
}
export const enum Orientation {
horizontal = "horizontal",
vertical = "vertical",
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








export class DividerTransformer implements ITranform {
    transform(value: any, obj: any, type: number) : any{
        if (type == 1) {
            return value.toString().replace(",", "|");
        } else {
            let strArray:Array<string> = value.toString().split("|");
            
            let valueArr:Array<Divider> = new Array<Divider>();
            for (let i =0; i < strArray.length; i++) {
                switch(strArray[i]) {
					case "none":
						valueArr.push(Divider.none);
                       	break;	
					case "beginning":
						valueArr.push(Divider.beginning);
                       	break;	
					case "end":
						valueArr.push(Divider.end);
                       	break;	
					case "middle":
						valueArr.push(Divider.middle);
                       	break;	
                }
                
            }
            return valueArr;
        }
    }
}




import {ViewGroupImpl_LayoutParams} from './ViewGroupImpl';

// end - imports
import {ViewGroupImpl} from './ViewGroupImpl';
export abstract class DefaultCustomHeadViewImpl<T> extends ViewGroupImpl<T>{
	//start - body
	static initialize() {
		TransformerFactory.getInstance().register("divider", new DividerTransformer());
    }	
	@decorate(Type(() => CommandAttr))
	@decorate(Expose({ name: "baselineAligned" }))
	baselineAligned!:CommandAttr<boolean>| undefined;
	@decorate(Type(() => CommandAttr))
	@decorate(Expose({ name: "baselineAlignedChildIndex" }))
	baselineAlignedChildIndex!:CommandAttr<number>| undefined;
	@decorate(Type(() => CommandAttr))
	@decorate(Expose({ name: "divider" }))
	divider!:CommandAttr<string>| undefined;
	@decorate(Type(() => CommandAttr))
	@decorate(Expose({ name: "gravity" }))
	gravity!:CommandAttr<Gravity[]>| undefined;
	@decorate(Type(() => CommandAttr))
	@decorate(Expose({ name: "measureWithLargestChild" }))
	measureWithLargestChild!:CommandAttr<boolean>| undefined;
	@decorate(Type(() => CommandAttr))
	@decorate(Expose({ name: "weightSum" }))
	weightSum!:CommandAttr<number>| undefined;
	@decorate(Type(() => CommandAttr))
	@decorate(Expose({ name: "showDividers" }))
	showDividers!:CommandAttr<Divider[]>| undefined;
	@decorate(Type(() => CommandAttr))
	@decorate(Expose({ name: "dividerPadding" }))
	dividerPadding!:CommandAttr<string>| undefined;
	@decorate(Type(() => CommandAttr))
	@decorate(Expose({ name: "orientation" }))
	orientation!:CommandAttr<Orientation>| undefined;

	@decorate(Exclude())
	protected thisPointer: T;	
	protected abstract getThisPointer(): T;
	reset() : T {	
		super.reset();
		this.baselineAligned = undefined;
		this.baselineAlignedChildIndex = undefined;
		this.divider = undefined;
		this.gravity = undefined;
		this.measureWithLargestChild = undefined;
		this.weightSum = undefined;
		this.showDividers = undefined;
		this.dividerPadding = undefined;
		this.orientation = undefined;
		return this.thisPointer;
	}
	constructor(id: string, path: string[], event:  string) {
		super(id, path, event);
		this.thisPointer = this.getThisPointer();
	}
	

	public tryGetBaselineAligned() : T {
		this.resetIfRequired();
		if (this.baselineAligned == null || this.baselineAligned == undefined) {
			this.baselineAligned = new CommandAttr<boolean>()
		}
		
		this.baselineAligned.setGetter(true);
		this.orderGet++;
		this.baselineAligned.setOrderGet(this.orderGet);
		return this.thisPointer;
	}
	
	public isBaselineAligned() : boolean {
		if (this.baselineAligned == null || this.baselineAligned == undefined) {
			this.baselineAligned = new CommandAttr<boolean>();
		}
		return this.baselineAligned.getCommandReturnValue();
	}
	public setBaselineAligned(value : boolean) : T {
		this.resetIfRequired();
		if (this.baselineAligned == null || this.baselineAligned == undefined) {
			this.baselineAligned = new CommandAttr<boolean>();
		}
		
		this.baselineAligned.setSetter(true);
		this.baselineAligned.setValue(value);
		this.orderSet++;
		this.baselineAligned.setOrderSet(this.orderSet);
		return this.thisPointer;
	}
		

	public tryGetBaselineAlignedChildIndex() : T {
		this.resetIfRequired();
		if (this.baselineAlignedChildIndex == null || this.baselineAlignedChildIndex == undefined) {
			this.baselineAlignedChildIndex = new CommandAttr<number>()
		}
		
		this.baselineAlignedChildIndex.setGetter(true);
		this.orderGet++;
		this.baselineAlignedChildIndex.setOrderGet(this.orderGet);
		return this.thisPointer;
	}
	
	public getBaselineAlignedChildIndex() : number {
		if (this.baselineAlignedChildIndex == null || this.baselineAlignedChildIndex == undefined) {
			this.baselineAlignedChildIndex = new CommandAttr<number>();
		}
		return this.baselineAlignedChildIndex.getCommandReturnValue();
	}
	public setBaselineAlignedChildIndex(value : number) : T {
		this.resetIfRequired();
		if (this.baselineAlignedChildIndex == null || this.baselineAlignedChildIndex == undefined) {
			this.baselineAlignedChildIndex = new CommandAttr<number>();
		}
		
		this.baselineAlignedChildIndex.setSetter(true);
		this.baselineAlignedChildIndex.setValue(value);
		this.orderSet++;
		this.baselineAlignedChildIndex.setOrderSet(this.orderSet);
		return this.thisPointer;
	}
		

	public tryGetDivider() : T {
		this.resetIfRequired();
		if (this.divider == null || this.divider == undefined) {
			this.divider = new CommandAttr<string>()
		}
		
		this.divider.setGetter(true);
		this.orderGet++;
		this.divider.setOrderGet(this.orderGet);
		return this.thisPointer;
	}
	
	public getDivider() : string {
		if (this.divider == null || this.divider == undefined) {
			this.divider = new CommandAttr<string>();
		}
		return this.divider.getCommandReturnValue();
	}
	public setDivider(value : string) : T {
		this.resetIfRequired();
		if (this.divider == null || this.divider == undefined) {
			this.divider = new CommandAttr<string>();
		}
		
		this.divider.setSetter(true);
		this.divider.setValue(value);
		this.orderSet++;
		this.divider.setOrderSet(this.orderSet);
		return this.thisPointer;
	}
		

	public tryGetGravity() : T {
		this.resetIfRequired();
		if (this.gravity == null || this.gravity == undefined) {
			this.gravity = new CommandAttr<Gravity[]>()
		}
		
		this.gravity.setGetter(true);
		this.orderGet++;
		this.gravity.setOrderGet(this.orderGet);
		return this.thisPointer;
	}
	
	public getGravity() : Gravity[] {
		if (this.gravity == null || this.gravity == undefined) {
			this.gravity = new CommandAttr<Gravity[]>();
		}
this.gravity.setTransformer('gravity');		return this.gravity.getCommandReturnValue();
	}
	public setGravity(...value : Gravity[]) : T {
		this.resetIfRequired();
		if (this.gravity == null || this.gravity == undefined) {
			this.gravity = new CommandAttr<Gravity[]>();
		}
		
		this.gravity.setSetter(true);
		this.gravity.setValue(value);
		this.orderSet++;
		this.gravity.setOrderSet(this.orderSet);
this.gravity.setTransformer('gravity');		return this.thisPointer;
	}
		

	public tryGetMeasureWithLargestChild() : T {
		this.resetIfRequired();
		if (this.measureWithLargestChild == null || this.measureWithLargestChild == undefined) {
			this.measureWithLargestChild = new CommandAttr<boolean>()
		}
		
		this.measureWithLargestChild.setGetter(true);
		this.orderGet++;
		this.measureWithLargestChild.setOrderGet(this.orderGet);
		return this.thisPointer;
	}
	
	public isMeasureWithLargestChild() : boolean {
		if (this.measureWithLargestChild == null || this.measureWithLargestChild == undefined) {
			this.measureWithLargestChild = new CommandAttr<boolean>();
		}
		return this.measureWithLargestChild.getCommandReturnValue();
	}
	public setMeasureWithLargestChild(value : boolean) : T {
		this.resetIfRequired();
		if (this.measureWithLargestChild == null || this.measureWithLargestChild == undefined) {
			this.measureWithLargestChild = new CommandAttr<boolean>();
		}
		
		this.measureWithLargestChild.setSetter(true);
		this.measureWithLargestChild.setValue(value);
		this.orderSet++;
		this.measureWithLargestChild.setOrderSet(this.orderSet);
		return this.thisPointer;
	}
		

	public tryGetWeightSum() : T {
		this.resetIfRequired();
		if (this.weightSum == null || this.weightSum == undefined) {
			this.weightSum = new CommandAttr<number>()
		}
		
		this.weightSum.setGetter(true);
		this.orderGet++;
		this.weightSum.setOrderGet(this.orderGet);
		return this.thisPointer;
	}
	
	public getWeightSum() : number {
		if (this.weightSum == null || this.weightSum == undefined) {
			this.weightSum = new CommandAttr<number>();
		}
		return this.weightSum.getCommandReturnValue();
	}
	public setWeightSum(value : number) : T {
		this.resetIfRequired();
		if (this.weightSum == null || this.weightSum == undefined) {
			this.weightSum = new CommandAttr<number>();
		}
		
		this.weightSum.setSetter(true);
		this.weightSum.setValue(value);
		this.orderSet++;
		this.weightSum.setOrderSet(this.orderSet);
		return this.thisPointer;
	}
		

	public tryGetShowDividers() : T {
		this.resetIfRequired();
		if (this.showDividers == null || this.showDividers == undefined) {
			this.showDividers = new CommandAttr<Divider[]>()
		}
		
		this.showDividers.setGetter(true);
		this.orderGet++;
		this.showDividers.setOrderGet(this.orderGet);
		return this.thisPointer;
	}
	
	public getShowDividers() : Divider[] {
		if (this.showDividers == null || this.showDividers == undefined) {
			this.showDividers = new CommandAttr<Divider[]>();
		}
this.showDividers.setTransformer('divider');		return this.showDividers.getCommandReturnValue();
	}
	public setShowDividers(...value : Divider[]) : T {
		this.resetIfRequired();
		if (this.showDividers == null || this.showDividers == undefined) {
			this.showDividers = new CommandAttr<Divider[]>();
		}
		
		this.showDividers.setSetter(true);
		this.showDividers.setValue(value);
		this.orderSet++;
		this.showDividers.setOrderSet(this.orderSet);
this.showDividers.setTransformer('divider');		return this.thisPointer;
	}
		

	public tryGetDividerPadding() : T {
		this.resetIfRequired();
		if (this.dividerPadding == null || this.dividerPadding == undefined) {
			this.dividerPadding = new CommandAttr<string>()
		}
		
		this.dividerPadding.setGetter(true);
		this.orderGet++;
		this.dividerPadding.setOrderGet(this.orderGet);
		return this.thisPointer;
	}
	
	public getDividerPadding() : string {
		if (this.dividerPadding == null || this.dividerPadding == undefined) {
			this.dividerPadding = new CommandAttr<string>();
		}
		return this.dividerPadding.getCommandReturnValue();
	}
	public setDividerPadding(value : string) : T {
		this.resetIfRequired();
		if (this.dividerPadding == null || this.dividerPadding == undefined) {
			this.dividerPadding = new CommandAttr<string>();
		}
		
		this.dividerPadding.setSetter(true);
		this.dividerPadding.setValue(value);
		this.orderSet++;
		this.dividerPadding.setOrderSet(this.orderSet);
		return this.thisPointer;
	}
		

	public setOrientation(value : Orientation) : T {
		this.resetIfRequired();
		if (this.orientation == null || this.orientation == undefined) {
			this.orientation = new CommandAttr<Orientation>();
		}
		
		this.orientation.setSetter(true);
		this.orientation.setValue(value);
		this.orderSet++;
		this.orientation.setOrderSet(this.orderSet);
		return this.thisPointer;
	}
		
	//end - body

}
	
//start - staticinit
export abstract class DefaultCustomHeadViewImpl_LayoutParams<T> extends ViewGroupImpl_LayoutParams<T> {
	@decorate(Type(() => CommandAttr))
	@decorate(Expose({ name: "layout_gravity" }))
	layout_gravity!:CommandAttr<Gravity[]>| undefined;
	@decorate(Type(() => CommandAttr))
	@decorate(Expose({ name: "layout_weight" }))
	layout_weight!:CommandAttr<number>| undefined;
	@decorate(Exclude())
	protected thisPointer: T;	
	protected abstract getThisPointer(): T;
	reset() : T {	
		super.reset();
		this.layout_gravity = undefined;
		this.layout_weight = undefined;
		return this.thisPointer;
	}
	constructor() {
		super();
		this.thisPointer = this.getThisPointer();
	}
	
	public tryGetLayoutGravity() : T {
		if (this.layout_gravity == null || this.layout_gravity == undefined) {
			this.layout_gravity = new CommandAttr<Gravity[]>()
		}
		
		this.layout_gravity.setGetter(true);
		this.orderGet++;
		this.layout_gravity.setOrderGet(this.orderGet);
		return this.thisPointer;
	}
	
	public getLayoutGravity() : Gravity[] {
		if (this.layout_gravity == null || this.layout_gravity == undefined) {
			this.layout_gravity = new CommandAttr<Gravity[]>();
		}
this.layout_gravity.setTransformer('gravity');		return this.layout_gravity.getCommandReturnValue();
	}
	public setLayoutGravity(...value : Gravity[]) : T {
		if (this.layout_gravity == null || this.layout_gravity == undefined) {
			this.layout_gravity = new CommandAttr<Gravity[]>();
		}
		this.layout_gravity.setSetter(true);
		this.layout_gravity.setValue(value);
		this.orderSet++;
		this.layout_gravity.setOrderSet(this.orderSet);
this.layout_gravity.setTransformer('gravity');		return this.thisPointer;
	}
	public tryGetLayoutWeight() : T {
		if (this.layout_weight == null || this.layout_weight == undefined) {
			this.layout_weight = new CommandAttr<number>()
		}
		
		this.layout_weight.setGetter(true);
		this.orderGet++;
		this.layout_weight.setOrderGet(this.orderGet);
		return this.thisPointer;
	}
	
	public getLayoutWeight() : number {
		if (this.layout_weight == null || this.layout_weight == undefined) {
			this.layout_weight = new CommandAttr<number>();
		}
		return this.layout_weight.getCommandReturnValue();
	}
	public setLayoutWeight(value : number) : T {
		if (this.layout_weight == null || this.layout_weight == undefined) {
			this.layout_weight = new CommandAttr<number>();
		}
		this.layout_weight.setSetter(true);
		this.layout_weight.setValue(value);
		this.orderSet++;
		this.layout_weight.setOrderSet(this.orderSet);
		return this.thisPointer;
	}
}

export class DefaultCustomHeadView_LayoutParams extends DefaultCustomHeadViewImpl_LayoutParams<DefaultCustomHeadView_LayoutParams> implements ILayoutParam {
    getThisPointer(): DefaultCustomHeadView_LayoutParams {
        return this;
    }

   	constructor() {
		super();	
	}
}

export class DefaultCustomHeadView extends DefaultCustomHeadViewImpl<DefaultCustomHeadView> implements IWidget{
    getThisPointer(): DefaultCustomHeadView {
        return this;
    }
    
   	public getClass() {
		return DefaultCustomHeadView;
	}
	
   	constructor(id: string, path: string[], event: string) {
		super(id, path, event);	
	}
}

DefaultCustomHeadViewImpl.initialize();

//end - staticinit
