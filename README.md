## Refleksi 1
# pada modul/tutorial ini saya sudah mencoba untuk mengimplementasi prinsip-prinsip clean code (meaningful names, method, dan self-documenting code) sehingga tidak perlu terlalu banyak comment untuk menjelaskan codenya.
# saya melihat kurangnya data validation pada form product, saya ganti kodenya agar input required pada input nama dan min=0 pada input quantity agar quantity non negative.
# ada juga menemukan kesalahan-kesalahan besar di source code modul/tutorial. cth:  quantity product menerima int di model,tapi di form menerima text.

## Refleksi 2
# unit test perlu dibuat sampai coverage berada di sekitar 75-85% MINIMUM, agar mayoritas kode kita sudah yakin dapat dirun dengan baik.
# namun untuk testing code selengkap apapun (bahkan 100% coverage) itu hanya memverifikasi kalau code sesuai denagn test care sintetis yang kita buat, bisa saja kita lupa memverifikasi suatu function di dalam source codem jadi belum tentu source code 100% bugfree.
# jika sebuah test baru dibuat dengan setup dan instance variable yang sama, maka cleanliness dari kode turun, karena 
1) code akan terus terduplikat yang melanggar konsep "Dont Repeat Yourself" sehingga code yang telah di duplicate redundant
2) jika ada perubahan pada source code dan bagian yang telah terduplikasi, maka kita harus merubah setiap testnya.
# Solusi : pakai inheritance-class dan abstract-class, setup dan instantiate variables bisa dilakukan di class tersebut, lalu test-test selanjutnya inherit dari abstract-class tersebut. 
