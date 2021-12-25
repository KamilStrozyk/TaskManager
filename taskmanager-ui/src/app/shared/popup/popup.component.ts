import { Component, Inject } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import *  as  translations from "../../../assets/translations.json"

@Component({
    selector: 'popup',
    templateUrl: 'popup.component.html',
})
export class PopupComponent {

    constructor(
        public dialogRef: MatDialogRef<PopupComponent>,
        @Inject(MAT_DIALOG_DATA) public data: any) {
        this.chosenTranslation = data.chosenLanguage;
        this.result = data.fields;
        this.title = data.title;
        this.confirm = data.confirm;
        this.message = data.message;
    }
    translations: any = translations;
    chosenTranslation: any;
    message: any;
    title: string;
    confirm: string;
    result: any = [];
    
    onNoClick(): void {
        this.dialogRef.close();
    }
}

export interface IPopupData {
    name : string;
    data: string;
}

export class PopupData implements IPopupData {
    name : string;
    data: string;

    constructor(data?: IPopupData) {
        if (data) {
            for (let property in data) {
                if (data.hasOwnProperty(property)) {
                    (this as any)[property] = (data as any)[property];
                }
            }
        }
    }
}

