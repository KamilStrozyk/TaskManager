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
    }
    translations: any = translations;
    chosenTranslation: any
    result: string = ""
    onNoClick(): void {
        this.dialogRef.close();
    }

}
