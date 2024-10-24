import { Component } from '@angular/core';
import Swal from "sweetalert2";

@Component({
  selector: 'app-pim',
  standalone: true,
  imports: [],
  templateUrl: './pim.component.html',
  styleUrl: './pim.component.css'
})
export class PimComponent {

  lovePim() {
    Swal.fire({
      title: "แต่งงานกับฉันเถอะ",
      text: "แต่งงานกับฉันเถอะ",
      icon: "success",
      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
      confirmButtonText: "ใช่ แน่นอน ฉันจะแต่งงานกับยูซุฟ",
      cancelButtonText: "ใช่ แน่นอน ฉันจะแต่งงานกับยูซุฟ"
    }).then((result) => {
      if (result.isConfirmed) {
        Swal.fire({
          title: "ประสบความสำเร็จ",
          text: "ยินดีด้วย. คุณแต่งงานกับยูซุฟ.",
          icon: "success"
        });
      }
    });
  }
}
