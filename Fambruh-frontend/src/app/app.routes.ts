import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';

import { UserGuard } from './service/user.guard';
export const routes: Routes = [
{path:'',component:HomeComponent},


{path:'login',
     loadComponent:()=> 
     import('./Website/login/login.component').then((c)=>c.LoginComponent)},

{path:'blogs',
     loadComponent:()=>
     import('./User/blogs/blogs.component').then((c)=>c.BlogsComponent)},

{path:'blog/:id',
     loadComponent:()=>
     import('./User/blogdetail/blogdetail.component').then((c)=>c.BlogdetailComponent)},

{path:'product/:id',
     loadComponent:()=> 
     import('./Website/productdetail/productdetail.component').then((c)=>c.ProductdetailComponent)},

{path:'cart',
     loadComponent:()=> 
     import('./User/cartitem/cartitem.component').then((c)=>c.CartitemComponent)},

{path:'cart/shippingaddress',
    loadComponent:()=> 
    import('./User/shippingform/shippingform.component').then((c)=>c.ShippingformComponent), 
    canActivate: [UserGuard],
},

{path:'dashboard',
    loadComponent:()=>
    import('./Admin/dashboard/dashboard.component').then((c)=>c.DashboardComponent)},

{path:'productform',
    loadComponent:()=>
    import('./Admin/productform/productform.component').then((c)=>c.ProductformComponent)},

{path:'blogform',
    loadComponent:()=> 
    import('./Admin/blogform/blogform.component').then((c)=>c.BlogformComponent)},

{path:'orderplaced/:oid',
    loadComponent:()=>
    import('./User/orderplaced/orderplaced.component').then((c)=>c.OrderplacedComponent)},

    {path:'profile',
        loadComponent:()=>
            import('./User/profile/profile.component').then((c)=>c.ProfileComponent)
    },

    {path:'set-password',
        loadComponent:()=>
            import('./User/passwordreset/passwordreset.component').then((c)=>c.PasswordresetComponent)
    },

    {path:'login/forgot-password',
        loadComponent:()=>
            import('./User/passwordforgot/passwordforgot.component').then((c)=>c.PasswordforgotComponent)
    },

    {path:'affiliate',
        loadComponent:()=>
            import('./User/affiliate/affiliate.component').then((c)=>c.AffiliateComponent)
        },
        {path:'wishlist',
            loadComponent:()=>
                import('./User/wislist/wislist.component').then((c)=>c.WislistComponent)
            
        },
        {path:'signup/:referralCode',
            loadComponent:()=>
                import('./User/signupreferal/signupreferal.component').then((c)=>c.SignupreferalComponent)
        },
        {
            path:'affiliatedashboard',
            loadComponent:()=>
                import('./User/affiliatedashboard/affiliatedashbaord.component').then((c)=>c.AffiliatedashbaordComponent)
        },
        {
            path:'terms&conditions',
            loadComponent:()=>
                import('./Website/termscondition/termscondition.component').then((c)=>c.TermsconditionComponent)
        },
        {
            path:'faq',
            loadComponent:()=>
                import('./Website/faq/faq.component').then((c)=>c.FaqComponent)
        }
];
