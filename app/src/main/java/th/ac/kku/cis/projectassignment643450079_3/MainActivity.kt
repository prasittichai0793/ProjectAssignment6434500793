package th.ac.kku.cis.projectassignment643450079_3

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.firestore.FirebaseFirestore
import th.ac.kku.cis.projectassignment643450079_3.ui.theme.ProjectAssignment6434500793Theme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // ติดตั้งธีมของแอพพลิเคชัน
            ProjectAssignment6434500793Theme {
                // กำหนด NavController โดยใช้ remember
                val navController = rememberNavController()

                // กำหนด NavHost ซึ่งเป็นตัวรับและจัดการ Fragment
                NavHost(navController = navController, startDestination = "selectButtonScreen") {
                    // กำหนด Fragment สำหรับหน้าจอเลือกปุ่ม
                    composable("selectButtonScreen") {
                        SelectButtonScreen(navController)
                    }

                    // กำหนด Fragment สำหรับหน้าจอล็อกอินของผู้ดูแลระบบ
                    composable("adminLoginScreen") {
                        AdminLoginScreen(navController)
                    }
                    // กำหนด Fragment สำหรับหน้าจอลงทะเบียนของผู้ดูแลระบบ
                    composable("registerAdminScreen") {
                        RegisterAdminScreen(navController)
                    }
                    // กำหนด Fragment สำหรับหน้าจอแสดงข้อมูลของผู้ดูแลระบบ
                    composable("displayAdminScreen/{email}/{password}") { backStackEntry ->
                        val email = backStackEntry.arguments?.getString("email") ?: ""
                        val password = backStackEntry.arguments?.getString("password") ?: ""
                        DisplayAdminScreen(navController, email, password)
                    }
                    // กำหนด Fragment สำหรับหน้าจอแสดงข้อมูลของสินค้า
                    composable("showDataAdminScreen") {
                        ShowDataAdminScreen(navController)
                    }
                    // กำหนด Fragment สำหรับหน้าจอแสดงข้อมูลสินค้าเมื่อต้องการอัปเดต
                    composable(
                        route = "updateProductScreen/{productName}",
                        arguments = listOf(navArgument("productName") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val productName = backStackEntry.arguments?.getString("productName") ?: ""
                        UpdateProductScreen(productName, navController)
                    }

                    // กำหนด Fragment สำหรับหน้าจอล็อกอินของผู้ใช้ทั่วไป
                    composable("userLoginScreen") {
                        UserLoginScreen(navController)
                    }
                    // กำหนด Fragment สำหรับหน้าจอลงทะเบียนของผู้ใช้ทั่วไป
                    composable("registerUserScreen") {
                        RegisterUserScreen(navController)
                    }
                    // กำหนด Fragment สำหรับหน้าจอแสดงข้อมูลของผู้ใช้ทั่วไป
                    composable("displayUserScreen/{email}/{password}") { backStackEntry ->
                        val email = backStackEntry.arguments?.getString("email") ?: ""
                        val password = backStackEntry.arguments?.getString("password") ?: ""
                        DisplayUserScreen(navController, email, password)
                    }
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectButtonScreen(navController: NavHostController) {
    // สร้างพื้นผิวของหน้าจอด้วย Surface โดยเต็มพื้นที่ของหน้าจอ
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        // กำหนดรูปแบบการจัดวางเนื้อหาใน Column
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // สร้าง TopAppBar สำหรับแสดงหัวข้อของหน้าจอ
//            TopAppBar(
//                title = {
//                    Text(
//                        text = "Select",
//                        modifier = Modifier.fillMaxWidth(),
//                        textAlign = TextAlign.Center
//                    )
//                },
//                Modifier.background()
//            )

            TopAppBar(
                title = {
                    Text(
                        text = "Select",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                modifier = Modifier.background(color = Color(0xFF006400)) // สีเขียวเข้ม
            )


//            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // สร้างปุ่ม Admin และ User ซึ่งเมื่อคลิกจะทำการเปลี่ยนหน้าจอ
                Row(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Button(
                        onClick = {
                            navController.navigate("AdminLoginScreen") // ทำการเปลี่ยนหน้าจอไปยังหน้าจอล็อกอินของ Admin
                        },
                        modifier = Modifier
                            .padding(8.dp)
                            .height(48.dp)
                            .width(120.dp)
                    ) {
                        Text("Admin") // ข้อความบนปุ่ม Admin
                    }

                    Button(
                        onClick = {
                            navController.navigate("UserLoginScreen") // ทำการเปลี่ยนหน้าจอไปยังหน้าจอล็อกอินของ User
                        },
                        modifier = Modifier
                            .padding(8.dp)
                            .height(48.dp)
                            .width(120.dp)
                    ) {
                        Text("User") // ข้อความบนปุ่ม User
                    }
                }
            }
        }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminLoginScreen(navController: NavHostController) {
    // กำหนดตัวแปรสำหรับเก็บค่าอีเมลและรหัสผ่าน
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    // สร้างพื้นผิวของหน้าจอด้วย Surface โดยเต็มพื้นที่ของหน้าจอ
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        // กำหนดรูปแบบการจัดวางเนื้อหาใน Column
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // สร้าง TopAppBar สำหรับแสดงหัวข้อของหน้าจอ และปุ่มย้อนกลับ
            TopAppBar(
                title = {
                    Text(
                        text = "Admin Login",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.padding(horizontal = 12.dp) // ระยะของไอคอนจากขอบจอด้านซ้าย
                    ) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black // เปลี่ยนสีของ IconButton เป็นสีเขียว
                        )
                    }
                }
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // สร้างช่องใส่ข้อมูลอีเมลและรหัสผ่าน
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.padding(8.dp)
                        .width(400.dp)
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(), // ใช้ PasswordVisualTransformation เพื่อซ่อน password
                    modifier = Modifier.padding(8.dp)
                        .width(400.dp)
                )

                // แสดงข้อความผิดพลาดหากมี
                if (errorMessage.isNotEmpty()) {
                    Text(errorMessage)
                }

                // สร้างปุ่มเพื่อล็อกอินหรือสมัครสมาชิก
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            CheckLoginAdmin(email, password) { isSuccess ->
                                if (isSuccess) {
                                    navController.navigate("DisplayAdminScreen/$email/$password")
                                } else {
                                    errorMessage = "Incorrect email or password. Please try again."
                                }
                            }
                        },
                        modifier = Modifier
                            .padding(8.dp)
                            .height(48.dp)
                            .width(120.dp)
                    ) {
                        Text("Login")
                    }
                    Button(
                        onClick = {
                            navController.navigate("registerAdminScreen") // ทำการเปลี่ยนหน้าจอไปยังหน้าจอลงทะเบียน
                        },
                        modifier = Modifier
                            .padding(8.dp)
                            .height(48.dp)
                            .width(120.dp)
                    ) {
                        Text("Register")
                    }
                }
            }
        }
    }
}

// ฟังก์ชันสำหรับตรวจสอบการล็อกอินของผู้ดูแลระบบ
fun CheckLoginAdmin(email: String, password: String, onResult: (Boolean) -> Unit) {
    val db = Firebase.firestore
    db.collection("RegisterAdmin")
        .whereEqualTo("email", email)
        .whereEqualTo("password", password)
        .get()
        .addOnSuccessListener { result ->
            onResult(!result.isEmpty)
        }
        .addOnFailureListener { e ->
            println("Error getting documents: $e")
            onResult(false)
        }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterAdminScreen(navController: NavHostController) {
    // กำหนดตัวแปรสำหรับเก็บชื่อ อีเมล และรหัสผ่าน รวมถึงข้อความผิดพลาด
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    // สร้างพื้นผิวของหน้าจอด้วย Surface โดยเต็มพื้นที่ของหน้าจอ
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        // กำหนดรูปแบบการจัดวางเนื้อหาใน Column
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // สร้าง TopAppBar สำหรับแสดงหัวข้อของหน้าจอ และปุ่มย้อนกลับ
            TopAppBar(
                title = {
                    Text(
                        text = "Admin Register", // ข้อความที่จะแสดงบน TopAppBar
                        modifier = Modifier.fillMaxWidth(), // ขยายขนาดเต็มความกว้าง
                        textAlign = TextAlign.Center // จัดวางข้อความตรงกลาง
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black // เปลี่ยนสีของ IconButton เป็นสีเขียว
                        ) // ไอคอนสำหรับปุ่มย้อนกลับ
                    }
                }
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // สร้างช่องใส่ข้อมูลชื่อ อีเมล และรหัสผ่าน
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") }, // ป้ายชื่อให้กับช่องข้อมูลชื่อ
                    modifier = Modifier.padding(8.dp)
                        .width(400.dp)
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") }, // ป้ายชื่อให้กับช่องข้อมูลอีเมล
                    modifier = Modifier.padding(8.dp)
                        .width(400.dp)
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") }, // ป้ายชื่อให้กับช่องข้อมูลรหัสผ่าน
                    modifier = Modifier.padding(8.dp)
                        .width(400.dp)
                )

                // แสดงข้อความผิดพลาดหากมี
                if (errorMessage.isNotEmpty()) {
                    Text(errorMessage)
                }

                // สร้างปุ่มเพื่อทำการลงทะเบียน
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            // เรียกใช้ฟังก์ชันเพื่อบันทึกข้อมูลลงใน Firestore และนำผู้ใช้กลับไปหน้าหลัก
                            saveRegisterAdminToFirestore(name, email, password)
                            navController.navigate("adminLoginScreen") // ทำการเปลี่ยนหน้าจอไปยังหน้าจอล็อกอินของ Admin
                        },
                        modifier = Modifier
                            .padding(8.dp)
                            .height(48.dp)
                            .width(120.dp)
                    ) {
                        Text("Register") // ข้อความบนปุ่ม
                    }
                }
            }
        }
    }
}

fun saveRegisterAdminToFirestore(name: String, email: String, password: String) {
    // สร้างอินสแตนซ์ของ Firebase Firestore
    val db = FirebaseFirestore.getInstance()

    // สร้างข้อมูลในรูปแบบของ HashMap เพื่อเก็บชื่อ, อีเมล, และรหัสผ่าน
    val data = hashMapOf(
        "name" to name,
        "email" to email,
        "password" to password
    )

    // เพิ่มข้อมูลลงในคอลเล็กชัน "RegisterAdmin" ใน Firestore
    db.collection("RegisterAdmin")
        .add(data)
        .addOnSuccessListener { documentReference ->
            // ในกรณีที่เพิ่มข้อมูลสำเร็จ
            Log.d("MainActivity", "DocumentSnapshot added with ID: ${documentReference.id}")
        }
        .addOnFailureListener { e ->
            // ในกรณีที่มีข้อผิดพลาดในการเพิ่มข้อมูล
            Log.w("MainActivity", "Error adding document", e)
        }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayAdminScreen(navController: NavHostController, email: String, password: String) {
    // กำหนดตัวแปรสำหรับเก็บชื่อสินค้า รายละเอียด ราคา รวมถึงข้อความผิดพลาด
    var productName by remember { mutableStateOf("") }
    var productDescription by remember { mutableStateOf("") }
    var productPrice by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    // สร้างพื้นผิวของหน้าจอด้วย Surface โดยเต็มพื้นที่ของหน้าจอ
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        // กำหนดรูปแบบการจัดวางเนื้อหาใน Column
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // สร้าง TopAppBar สำหรับแสดงหัวข้อของหน้าจอ และปุ่มย้อนกลับ และปุ่มออกจากระบบ
            TopAppBar(
                title = {
                    Text(
                        text = "Welcome Admin", // ข้อความที่จะแสดงบน TopAppBar
                        modifier = Modifier.fillMaxWidth(), // ขยายขนาดเต็มความกว้าง
                        textAlign = TextAlign.Center // จัดวางข้อความตรงกลาง
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {navController.popBackStack() }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black // เปลี่ยนสีของ IconButton เป็นสีเขียว
                        ) // ไอคอนสำหรับปุ่มย้อนกลับ
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate("selectButtonScreen") // ทำการเปลี่ยนหน้าจอไปยังหน้าจอเลือกปุ่ม
                    }) {
                        Icon(Icons.Filled.ExitToApp, contentDescription = "Logout", tint = Color(0xFF8B0000)) // ไอคอนสำหรับปุ่มออกจากระบบ
                    }
                }
            )

            // ช่องใส่ข้อมูลสินค้า
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // ข้อความ "เพิ่มสินค้า"
                Text(
                    text = "Add Product",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp, // ตั้งค่าขนาดตัวอักษรเป็น 24sp (sp หมายถึง scale-independent pixels)
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                // ช่องใส่ข้อมูลชื่อสินค้า
                OutlinedTextField(
                    value = productName,
                    onValueChange = { productName = it },
                    label = { Text("Product Name") }, // ป้ายชื่อให้กับช่องข้อมูลชื่อสินค้า
                    modifier = Modifier.padding(8.dp)
                        .width(400.dp)
                )

                // ช่องใส่ข้อมูลรายละเอียดสินค้า
                OutlinedTextField(
                    value = productDescription,
                    onValueChange = { productDescription = it },
                    label = { Text("Product Description") }, // ป้ายชื่อให้กับช่องข้อมูลรายละเอียดสินค้า
                    modifier = Modifier.padding(8.dp)
                        .width(400.dp)
                )

                // ช่องใส่ข้อมูลราคาสินค้า
                OutlinedTextField(
                    value = productPrice,
                    onValueChange = { productPrice = it },
                    label = { Text("Product Price") }, // ป้ายชื่อให้กับช่องข้อมูลราคาสินค้า
                    modifier = Modifier.padding(8.dp)
                        .width(400.dp)
                )

                // แสดงข้อความผิดพลาดหากมี
                if (errorMessage.isNotEmpty()) {
                    Text(errorMessage)
                }

                // สร้างปุ่มเพื่อเพิ่มสินค้า และปุ่มเพื่อแสดงรายการสินค้า
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            // ตรวจสอบข้อมูลที่ป้อน
                            if (productName.isNotEmpty() && productPrice.isNotEmpty() && productDescription.isNotEmpty()) {
                                saveProductToFirestore(productName, productPrice, productDescription)
                                // ล้างข้อมูลหลังจากบันทึก
                                productName = ""
                                productPrice = ""
                                productDescription = ""
                            } else {
                                errorMessage = "Please fill in all fields"
                            }
                        },
                        modifier = Modifier
                            .padding(8.dp)
                            .height(48.dp)
                            .width(150.dp)
                    ) {
                        Text("Add Product") // ข้อความบนปุ่ม
                    }

                    Button(
                        onClick = {
                            navController.navigate("showDataAdminScreen") // ทำการเปลี่ยนหน้าจอไปยังหน้าจอแสดงรายการสินค้า
                        },
                        modifier = Modifier
                            .padding(8.dp)
                            .height(48.dp)
                            .width(150.dp)
                    ) {
                        Text("Show Product") // ข้อความบนปุ่ม
                    }
                }
            }
        }
    }
}

fun saveProductToFirestore(name: String, price: String, description: String) {
    // เรียกใช้งาน Firebase Firestore
    val db = FirebaseFirestore.getInstance()
    // สร้างข้อมูลสินค้าเป็นแบบ HashMap
    val data = hashMapOf(
        "name" to name, // ชื่อสินค้า
        "price" to price, // ราคาสินค้า
        "description" to description // รายละเอียดสินค้า
    )
    // เพิ่มข้อมูลสินค้าลงในคอลเล็กชัน "Products" ใน Firestore
    db.collection("Products") // แทนที่ด้วยชื่อคอลเล็กชันจริงของคุณ
        .add(data)
        .addOnSuccessListener { documentReference ->
            Log.d("MainActivity", "DocumentSnapshot added with ID: ${documentReference.id}") // บันทึกข้อมูลการเพิ่มสำเร็จลงใน Log
        }
        .addOnFailureListener { e ->
            Log.w("MainActivity", "Error adding document", e) // บันทึกข้อผิดพลาดใน Log หากมีปัญหาในการเพิ่มข้อมูล
        }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowDataAdminScreen(navController: NavHostController) {
    // ตัวแปรสำหรับเก็บรายการสินค้าและข้อความผิดพลาด
    var productList by remember { mutableStateOf(listOf<String>()) }
    var errorMessage by remember { mutableStateOf("") }

    // ฟังก์ชันสำหรับดึงข้อมูลสินค้าจาก Firebase Firestore
    fun fetchProductList() {
        val db = FirebaseFirestore.getInstance()
        db.collection("Products")
            .get()
            .addOnSuccessListener { result ->
                productList = result.documents.map { document ->
                    val name = document["name"] as String
                    val price = document["price"] as String
                    val description = document["description"] as String
                    "$name - $price - $description"
                }
            }
            .addOnFailureListener { e ->
                Log.e("MainActivity", "Error getting documents: ", e)
                errorMessage = "Failed to fetch data from Firestore."
            }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // สร้าง TopAppBar สำหรับแสดงหัวข้อของหน้าจอ และปุ่มย้อนกลับ และปุ่มออกจากระบบ
            TopAppBar(
                title = {
                    Text(
                        text = "Product List", // ข้อความที่จะแสดงบน TopAppBar
                        modifier = Modifier.fillMaxWidth(), // ขยายขนาดเต็มความกว้าง
                        textAlign = TextAlign.Center // จัดวางข้อความตรงกลาง
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black // เปลี่ยนสีของ IconButton เป็นสีเขียว
                        ) // ไอคอนสำหรับปุ่มย้อนกลับ
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate("selectButtonScreen") // ทำการเปลี่ยนหน้าจอไปยังหน้าจอเลือกปุ่ม
                    }) {
                        Icon(Icons.Filled.ExitToApp, contentDescription = "Logout", tint = Color(0xFF8B0000)) // ไอคอนสำหรับปุ่มออกจากระบบ
                    }
                }
            )

            Spacer(modifier = Modifier.height(8.dp)) // เพิ่มระยะห่างด้านบน

            // แสดงรายการสินค้าที่ดึงมาได้
            LazyColumn {
                // แถวของส่วนหัว
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Name", // ข้อความ "ชื่อ"
                            fontWeight = FontWeight.Bold, // ตัวหนา
                            modifier = Modifier.weight(1f), // น้ำหนักเท่ากับ 1
                            textAlign = TextAlign.Center // จัดวางตรงกลาง
                        )
                        Text(
                            text = "Description", // ข้อความ "รายละเอียด"
                            fontWeight = FontWeight.Bold, // ตัวหนา
                            modifier = Modifier.weight(1f), // น้ำหนักเท่ากับ 1
                            textAlign = TextAlign.Center // จัดวางตรงกลาง
                        )
                        Text(
                            text = "Price", // ข้อความ "ราคา"
                            fontWeight = FontWeight.Bold, // ตัวหนา
                            modifier = Modifier.weight(1f), // น้ำหนักเท่ากับ 1
                            textAlign = TextAlign.Center // จัดวางตรงกลาง
                        )
                        Text(
                            text = "Edit", // ข้อความ "แก้ไข"
                            fontWeight = FontWeight.Bold, // ตัวหนา
                            modifier = Modifier.weight(1f), // น้ำหนักเท่ากับ 1
                            textAlign = TextAlign.Center // จัดวางตรงกลาง
                        )
                    }
                    Divider(color = Color.Black) // เส้นขั้นแบ่ง
                }

                // แถวของสินค้า
                items(productList) { product ->
                    val (name, description, price) = product.split(" - ")
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = name, // ชื่อสินค้า
                            modifier = Modifier.weight(1f), // น้ำหนักเท่ากับ 1
                            textAlign = TextAlign.Center // จัดวางตรงกลาง
                        )
                        Text(
                            text = price, // ราคาสินค้า
                            modifier = Modifier.weight(1f), // น้ำหนักเท่ากับ 1
                            textAlign = TextAlign.Center // จัดวางตรงกลาง
                        )
                        Text(
                            text = description, // รายละเอียดสินค้า
                            modifier = Modifier.weight(1f), // น้ำหนักเท่ากับ 1
                            textAlign = TextAlign.Center // จัดวางตรงกลาง
                        )

                        // เพิ่มปุ่มแก้ไขสำหรับแต่ละสินค้า
                        IconButton(
                            onClick = {
                                // ทำการเปลี่ยนหน้าจอไปยังหน้า updateProductScreen และส่งชื่อสินค้าไปด้วย
                                navController.navigate("updateProductScreen/$name")
                            }
                        ) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Color(0xFF006400)) // ไอคอนสำหรับปุ่มแก้ไข
                        }



                        // เพิ่มปุ่มลบสำหรับแต่ละสินค้า
                        IconButton(
                            onClick = {
                                // เรียกใช้ฟังก์ชันเพื่อลบสินค้าออกจาก Firestore
                                deleteProduct(name, navController)
                            }
                        ) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color(0xFF8B0000)) // ไอคอนสำหรับปุ่มลบ
                        }
                    }
                    Divider(color = Color.Black) // เส้นขั้นแบ่งระหว่างแถว
                }
            }

            // แสดงข้อความผิดพลาดหากมี
            if (errorMessage.isNotEmpty()) {
                Text(errorMessage)
            }
        }
    }

    // เรียกใช้ฟังก์ชันเมื่อหน้าจอแสดงข้อมูล
    LaunchedEffect(true) {
        fetchProductList()
    }
}



// ฟังก์ชันสำหรับลบสินค้าออกจาก Firestore
fun deleteProduct(productName: String, navController: NavHostController) {
    val db = FirebaseFirestore.getInstance()
    db.collection("Products")
        .whereEqualTo("name", productName)
        .get()
        .addOnSuccessListener { documents ->
            for (document in documents) {
                db.collection("Products")
                    .document(document.id)
                    .delete()
                    .addOnSuccessListener {
                        // ลบสำเร็จ
                        Log.d("MainActivity", "DocumentSnapshot successfully deleted!")
                        // รีเซ็ตหน้าจอโดยทำการเปลี่ยนหน้าจอไปยัง ShowDataAdmin ใหม่
                        navController.navigate("showDataAdminScreen")
                    }
                    .addOnFailureListener { e ->
                        // เกิดข้อผิดพลาดในการลบ
                        Log.w("MainActivity", "Error deleting document", e)
                    }
            }
        }
        .addOnFailureListener { exception ->
            Log.w("MainActivity", "Error getting documents: ", exception)
        }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateProductScreen(productName: String, navController: NavHostController) {
    var newName by remember { mutableStateOf("") }
    var newDescription by remember { mutableStateOf("") }
    var newPrice by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    // ฟังก์ชันในการดึงรายละเอียดสินค้า
    fun fetchProductDetails(productName: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("Products")
            .whereEqualTo("name", productName)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val name = document["name"] as String
                    val description = document["description"] as String
                    val price = document["price"] as String
                    newName = name
                    newDescription = description
                    newPrice = price
                }
            }
            .addOnFailureListener { exception ->
                Log.w("MainActivity", "Error getting documents: ", exception)
                errorMessage = "Failed to fetch product details."
            }
    }

    // ดึงรายละเอียดสินค้าเมื่อแสดงหน้าจอ
    LaunchedEffect(true) {
        fetchProductDetails(productName)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopAppBar(
                title = {
                    Text(
                        text = "Update Product",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate("selectButtonScreen")
                    }) {
                        Icon(Icons.Filled.ExitToApp, contentDescription = "Logout")
                    }
                }
            )

            // ช่องป้อนข้อมูลสำหรับรายละเอียดสินค้า
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = newName,
                    onValueChange = { newName = it },
                    label = { Text("Name") },
                    modifier = Modifier.padding(8.dp)
                        .width(400.dp)

                )

                OutlinedTextField(
                    value = newDescription,
                    onValueChange = { newDescription = it },
                    label = { Text("Description") },
                    modifier = Modifier.padding(8.dp)
                        .width(400.dp)
                )

                OutlinedTextField(
                    value = newPrice,
                    onValueChange = { newPrice = it },
                    label = { Text("Price") },
                    modifier = Modifier.padding(8.dp)
                        .width(400.dp)
                )

                Button(
                    onClick = {
                        // เรียกใช้ฟังก์ชันเพื่ออัปเดตสินค้าใน Firestore
                        updateProduct(productName, newName, newDescription, newPrice, navController)
                    }
                ) {
                    Text("Save")
                }

                if (errorMessage.isNotEmpty()) {
                    Text(errorMessage)
                }
            }
        }
    }
}

fun updateProduct(
    productName: String,
    newName: String,
    newDescription: String,
    newPrice: String,
    navController: NavHostController
) {
    val db = FirebaseFirestore.getInstance()
    db.collection("Products")
        .whereEqualTo("name", productName)
        .get()
        .addOnSuccessListener { documents ->
            for (document in documents) {
                db.collection("Products")
                    .document(document.id)
                    .update(
                        mapOf(
                            "name" to newName,
                            "description" to newDescription,
                            "price" to newPrice
                        )
                    )
                    .addOnSuccessListener {
                        // อัปเดตเรียบร้อย
                        Log.d("MainActivity", "DocumentSnapshot successfully updated!")
                        // นำกลับไปยังหน้าจอ ShowDataAdminScreen
                        navController.popBackStack()
                    }
                    .addOnFailureListener { e ->
                        // เกิดข้อผิดพลาดในการอัปเดต
                        Log.w("MainActivity", "Error updating document", e)
                    }
            }
        }
        .addOnFailureListener { exception ->
            Log.w("MainActivity", "Error getting documents: ", exception)
        }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserLoginScreen(navController: NavHostController) {
    // กำหนดตัวแปรสำหรับเก็บค่าอีเมลและรหัสผ่าน
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    // สร้างพื้นผิวของหน้าจอด้วย Surface โดยเต็มพื้นที่ของหน้าจอ
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        // กำหนดรูปแบบการจัดวางเนื้อหาใน Column
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // สร้าง TopAppBar สำหรับแสดงหัวข้อของหน้าจอ และปุ่มย้อนกลับ
            TopAppBar(
                title = {
                    Text(
                        text = "User Login", // ข้อความที่จะแสดงบน TopAppBar
                        modifier = Modifier.fillMaxWidth(), // ขยายขนาดเต็มความกว้าง
                        textAlign = TextAlign.Center // จัดวางข้อความตรงกลาง
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back") // ไอคอนสำหรับปุ่มย้อนกลับ
                    }
                }
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // สร้างช่องใส่ข้อมูลอีเมลและรหัสผ่าน
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.padding(8.dp)
                        .width(400.dp)
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(), // ใช้ PasswordVisualTransformation เพื่อซ่อน password
                    modifier = Modifier.padding(8.dp)
                        .width(400.dp)
                )

                // แสดงข้อความผิดพลาดหากมี
                if (errorMessage.isNotEmpty()) {
                    Text(errorMessage)
                }

                // สร้างปุ่มเพื่อล็อกอินหรือสมัครสมาชิก
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            CheckLoginUser(email, password) { isSuccess ->
                                if (isSuccess) {
                                    navController.navigate("DisplayUserScreen/$email/$password")
                                } else {
                                    errorMessage = "Incorrect email or password. Please try again."
                                }
                            }
                        },
                        modifier = Modifier
                            .padding(8.dp)
                            .height(48.dp)
                            .width(120.dp)
                    ) {
                        Text("Login")
                    }
                    Button(
                        onClick = {
                            navController.navigate("registerUserScreen") // ทำการเปลี่ยนหน้าจอไปยังหน้าจอลงทะเบียน
                        },
                        modifier = Modifier
                            .padding(8.dp)
                            .height(48.dp)
                            .width(120.dp)
                    ) {
                        Text("Register")
                    }
                }
            }
        }
    }
}

// ฟังก์ชันสำหรับตรวจสอบการล็อกอินของผู้ดูแลระบบ
fun CheckLoginUser(email: String, password: String, onResult: (Boolean) -> Unit) {
    val db = Firebase.firestore
    db.collection("RegisterUser")
        .whereEqualTo("email", email)
        .whereEqualTo("password", password)
        .get()
        .addOnSuccessListener { result ->
            onResult(!result.isEmpty)
        }
        .addOnFailureListener { e ->
            println("Error getting documents: $e")
            onResult(false)
        }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterUserScreen(navController: NavHostController) {
    // กำหนดตัวแปรสำหรับเก็บชื่อ อีเมล และรหัสผ่าน รวมถึงข้อความผิดพลาด
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    // สร้างพื้นผิวของหน้าจอด้วย Surface โดยเต็มพื้นที่ของหน้าจอ
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        // กำหนดรูปแบบการจัดวางเนื้อหาใน Column
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // สร้าง TopAppBar สำหรับแสดงหัวข้อของหน้าจอ และปุ่มย้อนกลับ
            TopAppBar(
                title = {
                    Text(
                        text = "User Register", // ข้อความที่จะแสดงบน TopAppBar
                        modifier = Modifier.fillMaxWidth(), // ขยายขนาดเต็มความกว้าง
                        textAlign = TextAlign.Center // จัดวางข้อความตรงกลาง
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back") // ไอคอนสำหรับปุ่มย้อนกลับ
                    }
                }
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") }, // ป้ายชื่อให้กับช่องข้อมูลชื่อ
                    modifier = Modifier.padding(8.dp)
                        .width(400.dp)
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") }, // ป้ายชื่อให้กับช่องข้อมูลอีเมล
                    modifier = Modifier.padding(8.dp)
                        .width(400.dp)
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") }, // ป้ายชื่อให้กับช่องข้อมูลรหัสผ่าน
                    modifier = Modifier.padding(8.dp)
                        .width(400.dp)
                )

                // แสดงข้อความผิดพลาดหากมี
                if (errorMessage.isNotEmpty()) {
                    Text(errorMessage)
                }

                // สร้างปุ่มเพื่อทำการลงทะเ
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            saveRegisterUserToFirestore(name, email, password)
                            navController.navigate("userLoginScreen") // ทำการเปลี่ยนหน้าจอ
                        },
                        modifier = Modifier
                            .padding(8.dp)
                            .height(48.dp)
                            .width(120.dp)
                    ) {
                        Text("Register")
                    }
                }
            }
        }
    }
}

fun saveRegisterUserToFirestore(name: String, email: String, password: String) {
    // สร้างอินสแตนซ์ของ Firebase Firestore
    val db = FirebaseFirestore.getInstance()
    // สร้างข้อมูลในรูปแบบของ HashMap เพื่อเก็บชื่อ, อีเมล, และรหัสผ่าน
    val data = hashMapOf(
        "name" to name,
        "email" to email,
        "password" to password
    )

    // เพิ่มข้อมูลลงในคอลเล็กชัน "Register๊หำพ" ใน Firestore
    db.collection("RegisterUser") // Replace with your actual collection name
        .add(data)
        .addOnSuccessListener { documentReference ->
            Log.d("MainActivity", "DocumentSnapshot added with ID: ${documentReference.id}")
        }
        .addOnFailureListener { e ->
            Log.w("MainActivity", "Error adding document", e)
        }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayUserScreen(navController: NavHostController, email: String, password: String) {
    // ตัวแปรสำหรับเก็บรายการสินค้า
    var productList by remember { mutableStateOf(listOf<String>()) }
    var errorMessage by remember { mutableStateOf("") }

    // ฟังก์ชันสำหรับดึงข้อมูลสินค้าจาก Firebase Firestore
    fun fetchProductList() {
        val db = FirebaseFirestore.getInstance()
        db.collection("Products")
            .get()
            .addOnSuccessListener { result ->
                productList = result.documents.map { document ->
                    val name = document["name"] as String
                    val price = document["price"] as String
                    val description = document["description"] as String
                    "$name - $price - $description"
                }
            }
            .addOnFailureListener { e ->
                Log.e("MainActivity", "Error getting documents: ", e)
                errorMessage = "Failed to fetch data from Firestore."
            }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // สร้าง TopAppBar สำหรับแสดงหัวข้อของหน้าจอ และปุ่มย้อนกลับ และปุ่มออกจากระบบ
            TopAppBar(
                title = {
                    Text(
                        text = "Product List", // ข้อความที่จะแสดงบน TopAppBar
                        modifier = Modifier.fillMaxWidth(), // ขยายขนาดเต็มความกว้าง
                        textAlign = TextAlign.Center // จัดวางข้อความตรงกลาง
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back") // ไอคอนสำหรับปุ่มย้อนกลับ
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate("selectButtonScreen") // ทำการเปลี่ยนหน้าจอ
                    }) {
                        Icon(Icons.Filled.ExitToApp, contentDescription = "Logout", tint = Color(0xFF8B0000)) // ไอคอนสำหรับปุ่มออกจากระบบ
                    }
                }
            )

            Spacer(modifier = Modifier.height(8.dp)) // เพิ่มระยะห่างด้านบน

            // แสดงรายการสินค้าที่ดึงมาได้
            LazyColumn {

                // แถวของส่วนหัว
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Name", // ข้อความ "ชื่อ"
                            fontWeight = FontWeight.Bold, // ตัวหนา
                            modifier = Modifier.weight(1f), // น้ำหนักเท่ากับ 1
                            textAlign = TextAlign.Center // จัดวางตรงกลาง
                        )
                        Text(
                            text = "Description", // ข้อความ "รายละเอียด"
                            fontWeight = FontWeight.Bold, // ตัวหนา
                            modifier = Modifier.weight(1f), // น้ำหนักเท่ากับ 1
                            textAlign = TextAlign.Center // จัดวางตรงกลาง
                        )
                        Text(
                            text = "Price", // ข้อความ "ราคา"
                            fontWeight = FontWeight.Bold, // ตัวหนา
                            modifier = Modifier.weight(1f), // น้ำหนักเท่ากับ 1
                            textAlign = TextAlign.Center // จัดวางตรงกลาง
                        )
                    }
                    Divider(color = Color.Black) // เส้นขั้นแบ่ง
                }

                // แถวของสินค้า
                items(productList) { product ->
                    val (name, description, price) = product.split(" - ")
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = name,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = price,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = description,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )
                    }
                    Divider(color = Color.Black)
                }
            }

            // แสดงข้อความผิดพลาดหากมี
            if (errorMessage.isNotEmpty()) {
                Text(errorMessage)
            }
        }
    }

    // เรียกใช้ฟังก์ชันเมื่อหน้าจอแสดงข้อมูล
    LaunchedEffect(true) {
        fetchProductList()
    }
}