import { Component, OnInit, ViewChild } from '@angular/core';
import { FormGroup, Validators, FormBuilder, FormArray, FormControl } from '@angular/forms';
import { CrudService } from '../crud.service';
import { Video } from '../model/video';
import { Level } from '../model/level';
import { Category } from '../model/category';
import { Router } from '@angular/router';
import { Referenceurl } from '../model/referenceurl';
@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.css']
})
export class AddComponent implements OnInit {

  levels: Array<Level>;
  categories: Array<Category>;
  videoForm: FormGroup;
  level: FormGroup;
  video: Video;
  addSuccessMessageFlag:boolean;
  constructor(private crudservice: CrudService,private router:Router, private formbuilder: FormBuilder) {

  }

  ngOnInit(): void {
    this.initializeForm();
    this.viewLevels();
    this.viewCategories();
    this.deleteRefArt(0);
    this.deleteRefUrl(0);
  }

  get videoControls(){
    return this.videoForm.controls;
  }
  initializeForm() {
    this.videoForm = this.formbuilder.group({
      name: ['',[Validators.required]],
      displayName: ['',[Validators.required]],
      url: ['',[Validators.required]],
      duration: [''],
      tags: ['',[Validators.required]],
      description: [''],
      level: this.formbuilder.group({
        id: ['']
      }),
      category: this.formbuilder.group({
        id: ['']
      }),
      transcript: [''],
      videoContent: this.formbuilder.array([this.formbuilder.group(
        {
          name: [''],
          file: [''],
          description: [''],
          type:['']
        }
      )]),
  
      referenceUrl: this.formbuilder.array([this.formbuilder.group(
        {
          name: [''],
          url: [''],
          description: ['']
        }
      )])
    });
  }

  get videoContent() {
    return this.videoForm.get('videoContent') as FormArray;
  }
  addRefArt() {
    this.videoContent.push(this.formbuilder.group({
      name: '',
      file: '',
      description: '',
      type:''
    }));
  }
  deleteRefArt(index: number) {
    this.videoContent.removeAt(index);
  }

  get referenceUrl() {
    return this.videoForm.get('referenceUrl') as FormArray;
  }
  addRefUrl() {
    this.referenceUrl.push(this.formbuilder.group({
      name: '',
      url: '',
      description: ''
    }));
  }
  deleteRefUrl(refUrlindex: number) {
    this.referenceUrl.removeAt(refUrlindex);
  }


  viewLevels() {
    this.crudservice.viewLevels().subscribe((result: any) => {
      this.levels = result.data;
      console.log(this.levels);
    });
  }

  viewCategories() {
    this.crudservice.viewCategories().subscribe((result: any) => {
      this.categories = result.data;
      console.log(this.categories);
    });
  }

  setLevelId(levelId: number) {
    this.videoForm.patchValue({ level: { id: levelId } });
  }

  setCategoryId(categoryId: number) {
    this.videoForm.patchValue({ category: { id: categoryId } });
  }
 newvideo:Video;
  save() {
     
    this.video = this.videoForm.value;
    console.log(this.video);
    // this.newvideo.referenceUrl=this.referenceUrl.value;
    // this.refurl.name=this.videoForm.get('referenceUrl.0').value;
    // console.log("new video value is"+this.refurl.name);
    this.crudservice.addVideo(this.video).subscribe((result: any) => {
      this.addSuccessMessageFlag=true;
    });

  }
  refurl:Referenceurl
  getVideo()
  {
    this.refurl.name=this.videoForm.get('referenceUrl.0').value;
    return this.video;
  }
  back()
    {
       this.router.navigate(['view']);
    }  
    
   
  fileName: string;
  file:File;
  selectedFile=null;
  uploadFile(event)
  {
     this.selectedFile=event.target.files[0];
     this.fileName = this.selectedFile.name;
     console.log('selectedFilesname: ' + this.fileName )
     const payload = new FormData();  
     payload.append('file', this.selectedFile);  
    this.crudservice.uploadFile(payload).subscribe((result:any)=>
   {
     console.log(result);
   }
   );
  }

}

